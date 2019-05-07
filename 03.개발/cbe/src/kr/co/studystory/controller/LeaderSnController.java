package kr.co.studystory.controller;
//���� �������� ��Ʈ�ѷ� ����

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.studystory.domain.DetailStudyNotice;
import kr.co.studystory.domain.Homework;
import kr.co.studystory.domain.NickAndId;
import kr.co.studystory.domain.StudyNameAndRecruit;
import kr.co.studystory.domain.StudyNotice;
import kr.co.studystory.service.StudyNoticeService;
import kr.co.studystory.vo.NewHomeworkVO;
import kr.co.studystory.vo.NewStudyNoticeVO;
import kr.co.studystory.vo.RecruitVO;
import kr.co.studystory.vo.SnAlarmVO;
import kr.co.studystory.vo.SnModifiedVO;

@Controller
public class LeaderSnController {
	@Autowired
	private StudyNoticeService sns;
	
	@RequestMapping(value="/study_notice/notice_list_leader.do", method= { GET, POST })
	public String leaderSnList(String s_num, Model model) {
		
		System.out.println("==========="+s_num);
		
		List<StudyNotice> list = sns.getSnList(s_num);
		
		// ���͵��� �������¸� ��ȯ�ؼ� ������� ��
		StudyNameAndRecruit snar = sns.getStudyNameAndRecruit(s_num);
		
		model.addAttribute("study_name", snar.getStudy_name());
		model.addAttribute("recruitment", snar.getRecruitment());
		model.addAttribute("snList", list);
		return "study_notice/notice_list_leader";
	}//leaderSnList
	
	@RequestMapping(value="/study_notice/change_recruit.do", method=POST)
	public String changeRecruit(RecruitVO rvo, Model model) {
		
		if(sns.changeRecruit(rvo)) {
			model.addAttribute("recruitChanged", true);
		}
		
		return "forward:../study_notice/notice_list_leader.do";
	}
	
	@RequestMapping(value="/study_notice/wrtie.do", method= { GET, POST })
	public String leaderWrite(String s_num, Model model) {
		
		List<NickAndId> list = sns.getMember(s_num);
		model.addAttribute("nickAndIdList", list);
		
		return "study_notice/notice_write";
	}
	
	
	@RequestMapping(value="/study_notice/wrtie_process.do", method=POST)
	public String leaderWriteProcess(NewStudyNoticeVO nsnvo, String hwNick, String hwWorkload, Model model) {
		
		if(sns.addNewSn(nsnvo)) {
			String sn_num = sns.getLatestSnNum(nsnvo.getS_num());
			
			// ���޹޴� hwNick�̳� hwWorkload�� �����ϸ� ���� �߰�
			if (!("".equals(hwNick) || "".equals(hwWorkload))) {
				String[] nicks = hwNick.split(",");
				String[] workloads = hwWorkload.split(",");
				
				String tempId = "";
				NewHomeworkVO nhwvo = null;
				
				if (nicks.length != 0) {
					for(int i=0; i<nicks.length; i++) {
						nhwvo = new NewHomeworkVO();
						tempId = sns.getIdByNick(nicks[i]);
						
						nhwvo.setId(tempId);
						nhwvo.setWorkload(workloads[i]);
						nhwvo.setSn_num(sn_num);
						
						sns.addNewHw(nhwvo); // ���� �߰�
					}
				}
			}
			
			SnAlarmVO savo = null;
			List<NickAndId> memberList = sns.getMember(nsnvo.getS_num());
			
			// ��� ������� �˸� �߰�
			NickAndId temp = null;
			for(int i=0; i<memberList.size(); i++) {
				temp = memberList.get(i);
				savo = new SnAlarmVO();
				savo.setCategory("���͵�");
				savo.setContent(temp.getNick()+"�� ["+nsnvo.getSubject()+"]�� ���ο� ���͵� ������ ��ϵǾ����ϴ�. Ȯ�����ּ���~.");
				savo.setId(temp.getId());
				savo.setSubject("���ο� ���͵� ���Ӱ����� ��ϵǾ����ϴ�.");
				sns.sendAlarm(savo);
			}
			
			model.addAttribute("snAddSuccessFlag", true);
		} else {
			model.addAttribute("snAddFailFlag", true);
		}
		
		return "forward:../study_notice/notice_list_leader.do?s_num="+nsnvo.getS_num();
	}
	
	@RequestMapping(value="/study_notice/modify.do", method=GET)
	public String leaderModifySn(String sn_num, String s_num, Model model) {
		
		DetailStudyNotice dsn= sns.getDetailSn(sn_num);
		List<Homework> hwList = sns.getHomework(sn_num);
		
		List<NickAndId> list = sns.getMember(s_num);
		model.addAttribute("nickAndIdList", list);
		model.addAttribute("snDetail", dsn);
		model.addAttribute("hwList", hwList);
		model.addAttribute("sn_num", sn_num);
		model.addAttribute("s_num", s_num);
		
 		return "study_notice/notice_modify";
	}
	
	@RequestMapping(value="/study_notice/modify_process.do", method=POST)
	public String leaderModifyProcess(SnModifiedVO smvo, String s_num, String hwNick, String hwWorkload, Model model) {
		
		if(sns.modifySn(smvo)) {
			String sn_num = smvo.getSn_num();
			
			// ���޹޴� hwNick�̳� hwWorkload�� �����ϸ� ���� �߰�
			if (!("".equals(hwNick) || "".equals(hwWorkload))) {
				// ���� ���������� �����۾�
				sns.removePrevHw(smvo.getSn_num());
				
				String[] nicks = hwNick.split(",");
				String[] workloads = hwWorkload.split(",");
				
				String tempId = "";
				NewHomeworkVO nhwvo = null;
				
				if (nicks.length != 0) {
					for(int i=0; i<nicks.length; i++) {
						nhwvo = new NewHomeworkVO();
						tempId = sns.getIdByNick(nicks[i]);
						
						nhwvo.setId(tempId);
						nhwvo.setWorkload(workloads[i]);
						nhwvo.setSn_num(sn_num);
						
						sns.addNewHw(nhwvo); // ���� �߰�
					}
				}
			}
			
			// ������ ��� ������� �˸��۾� �ʿ� ����
			model.addAttribute("snModifySuccessFlag", true);
		} else {
			model.addAttribute("snModifyFailFlag", true);
		}
		
		return "forward:../study_notice/notice_list_leader.do?";
	}
	
}//class
