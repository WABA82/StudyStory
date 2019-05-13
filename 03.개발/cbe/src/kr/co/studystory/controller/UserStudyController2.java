package kr.co.studystory.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.studystory.domain.JoinBbs;
import kr.co.studystory.domain.Joiner;
import kr.co.studystory.domain.MemberWithImg;
import kr.co.studystory.service.StudyGroupService2;
import kr.co.studystory.vo.ApplicantBbsVO;
import kr.co.studystory.vo.DetailJoinerVO;
import kr.co.studystory.vo.JoinDeleteVO;
import kr.co.studystory.vo.NewMemberVO;
import kr.co.studystory.vo.RefuseVO;

@Controller
public class UserStudyController2 {
	@Autowired
	private StudyGroupService2 sgs;

	/**
	 * ���� ���͵� ������(�̹�����) ���� 
	 * @param sNum
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/study_group/show_participants.do",method=RequestMethod.GET)
	public String studyMemberPage(HttpSession session, String s_num, Model model) {
		
		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		}
		
		List<MemberWithImg> mbwi=sgs.getMemberWithImg(s_num);
		
		
		model.addAttribute("total",mbwi.size());
		model.addAttribute("mbwi",mbwi );//��� (����) ����Ʈ
		//model.addAttribute("")//�� ����� dao���� ���ؿ��� 
		
		return "study_group/show_participants";
	}//studyMemberPage
	
	@RequestMapping(value="/study_group/new_joiner.do",method= {GET, POST})
	public String appliedMemberPage(HttpSession session, ApplicantBbsVO abvo, Model model) {
		
		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		}
	
		if(abvo.getCurrPage()==0) {
			abvo.setCurrPage(1);
		}
		String s_num=abvo.getS_num();
		
		int currPage= abvo.getCurrPage();
		int totalCnt=sgs.getJoinerTotal(s_num);            //
		int begin = sgs.beginNum(currPage);
		int end = sgs.endNum(begin);
		
		System.out.println(totalCnt+"��Ż cnt");//��Ż cnt�� �� ����
		int pageScale=sgs.pageScale();
		
		abvo.setBegin(begin);
		abvo.setEnd(end);
		
		List<JoinBbs> jb=sgs.getJoinerList(abvo);
		
		int totalPage=sgs.getTotalPage(totalCnt);
		int pageIndexNum =sgs.pageIndexNum();
		int startPage= sgs.startPage(currPage, pageIndexNum);
		int endPage=sgs.endPage(startPage, pageIndexNum, totalPage);
		
		model.addAttribute("forwardFlag", false);
		model.addAttribute("backwardFlag", false);
		
		if(currPage>pageIndexNum) {
			model.addAttribute("forwardFlag",true);
		}
		if(totalPage>endPage) {
			model.addAttribute("backwardFlag",true);
		}
		
		model.addAttribute("total",jb.size());///??�ʿ���°���
		model.addAttribute("jb",jb);
		
		model.addAttribute("pageScale", pageScale);
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currPage", currPage);
		model.addAttribute("s_num",s_num);
		
		return "study_group/new_joiner";
	}//appliedMemberPage
	
	@RequestMapping(value="/study_group/req_detail.do", method=RequestMethod.GET)
	public String detailAppliedMember(HttpSession session, DetailJoinerVO djvo, Model model) {
		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		}
		
		Joiner jr=sgs.getJoiner(djvo);
		
		model.addAttribute("jrInfo",jr);
		
		return "study_group/req_detail";
	}
	
	@RequestMapping(value="/study_group/req_accept.do", method=RequestMethod.GET)
	public String acceptMember(HttpSession session, NewMemberVO nmvo, Model model, JoinDeleteVO jdvo) {
		boolean acceptFlag =false;
		
		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		}
		
		// member�� �߰� + �˶� ������
		 acceptFlag=sgs.addNewMember(nmvo);
		 
		//JoinAlarmVO ja_vo=new JoinAlarmVO();
		if(acceptFlag) {
			// join���� ����
			sgs.removeJoin(new JoinDeleteVO(nmvo.getId(), nmvo.getS_num()));
		}
			
		return "forward:../study_group/new_joiner.do";
	}
	
	@RequestMapping(value="/study_group/req_reject.do", method=RequestMethod.GET)
	public String refuseApplicantPage(HttpSession session, String id, Model model) {
		
		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		}
		
		model.addAttribute("id", id);
		
		return "study_group/req_reject";
	}
	
	@RequestMapping(value="/study_group/req_reject_proc.do", method=RequestMethod.POST)
	public String refuseApplicantProcess(HttpSession session, RefuseVO rfvo, Model model ) {
		boolean refuseFlag=false;
		
		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		}
		
		System.out.println("================ ������ VO : "+rfvo);
		
		refuseFlag = sgs.removeJoin(new JoinDeleteVO(rfvo.getId(), rfvo.getS_num()));
		if(refuseFlag) {
			//�˶� ������ - ����
			sgs.sendRefuseAlarm(rfvo);
		}
		
		return "forward:../study_group/new_joiner.do";
	}
	
	
}

