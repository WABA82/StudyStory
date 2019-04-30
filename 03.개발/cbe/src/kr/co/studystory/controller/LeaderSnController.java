package kr.co.studystory.controller;
//���� �������� ��Ʈ�ѷ� ����

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.studystory.domain.NickAndId;
import kr.co.studystory.domain.StudyNotice;
import kr.co.studystory.service.StudyNoticeService;
import kr.co.studystory.vo.NewHomeworkVO;
import kr.co.studystory.vo.NewStudyNoticeVO;
import kr.co.studystory.vo.RecruitVO;
import kr.co.studystory.vo.SnAlarmVO;
import kr.co.studystory.vo.SnModifiedVO;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class LeaderSnController {
	@Autowired
	private StudyNoticeService sns;
	
	@RequestMapping(value="/study_notice/notice_list_leader.do", method= { GET, POST })
	public String leaderSnList(String sNum, Model model) {
		List<StudyNotice> list = sns.getSnList(sNum);
		model.addAttribute("snList", list);
		return "study_notice/notice_list_leader";
	}//leaderSnList
	
	@RequestMapping(value="/study_notice/change_recruit.do", method=POST)
	public String changeRecruit(RecruitVO rvo, Model model) {
		
		if(sns.changeRecruit(rvo)) {
			model.addAttribute("recruitChanged", true);
			model.addAttribute("recruitment", rvo.getStatus());
		}
		
		return "study_notice/notice_list_leader";
	}
	
	@RequestMapping(value="/study_notice/wrtie.do", method= { GET, POST })
	public String leaderWrite(String sNum, Model model) {
		
		List<NickAndId> list = sns.getMember(sNum);
		model.addAttribute("nickAndIdList", list);
		
		return "study_notice/notice_write";
	}
	
	
	@RequestMapping(value="/study_notice/wrtie_process.do", method=POST)
	public String leaderWriteProcess(NewStudyNoticeVO nsnvo, String hwNick, String hwWorkload, Model model) {
		
		if(sns.addNewSn(nsnvo)) {
			System.out.println("-------�������� �߰��Ϸ�");
			String sn_num = sns.getLatestSnNum(nsnvo.getS_num());
			
			String[] nicks = hwNick.split(",");
			String[] workloads = hwWorkload.split(",");
			
			String tempId = "";
			NewHomeworkVO nhwvo = null;
			SnAlarmVO savo = null;
			for(int i=0; i<nicks.length; i++) {
				nhwvo = new NewHomeworkVO();
				tempId = sns.getIdByNick(nicks[i]);
				
				nhwvo.setId(tempId);
				nhwvo.setWorkload(workloads[i]);
				nhwvo.setSn_num(sn_num);
				System.out.println("�߰��� ���� -------------"+nhwvo);
				
				sns.addNewHw(nhwvo); // ���� �߰�
				// �˸� �߰�
				savo = new SnAlarmVO();
				savo.setCategory("���͵�");
				savo.setContent(nicks[i]+"�� ["+nsnvo.getSubject()+"]�� ���ο� ���͵� ������ ��ϵǾ����ϴ�. Ȯ�����ּ���~.");
				savo.setId(tempId);
				savo.setSubject("���ο� ���͵� ���Ӱ����� ��ϵǾ����ϴ�.");
				
				System.out.println("�߰��� �˸�------------------"+savo);
				
				sns.sendAlarm(savo);
			}
			model.addAttribute("snAddSuccessFlag", true);
		} else {
			model.addAttribute("snAddFailFlag", true);
		}
		model.addAttribute("sNum", nsnvo.getS_num());
		
		return "study_notice/notice_list_leader";
	}
	
	@RequestMapping(value="/study_notice/modify.do", method=GET)
	public String leaderModifySn(String snNum, Model model) {
		
		return "study_notice/notice_modify";
	}
	
	@RequestMapping(value="/study_notice/modify_process.do", method=POST)
	public String leaderModifyProcess(SnModifiedVO smvo, Model model) {
		
		return "study_notice/notice_list_leader";
	}
	
}//class
