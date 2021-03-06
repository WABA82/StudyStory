package kr.co.studystory.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.studystory.domain.AppliedStudy;
import kr.co.studystory.domain.MyStudy;
import kr.co.studystory.domain.StudyIMade;
import kr.co.studystory.service.StudyGroupService3;
import kr.co.studystory.vo.ConditionVO;

@Controller
public class UserStudyController3 {
	@Autowired
	private StudyGroupService3 sgs3;
	
	@RequestMapping(value="/study_group/my_study.do", method= { GET,POST })
	public String myStudyPage(ConditionVO cvo, HttpSession session, Model model) {
		
		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		}
		
		if (cvo.getCategory() == null) {
			cvo.setCategory("none");
		}
		if (cvo.getLoc() == null) {
			cvo.setLoc("none");
		}
		
		cvo.setId((String)session.getAttribute("id"));
		
		List<MyStudy> myStudyList = sgs3.getMyStudy(cvo);
		List<AppliedStudy> appliedList = sgs3.getMyApplied(cvo);
		
		MyStudy temp = null;
		int closedStudy = 0;
		
		for(int i=0; i<myStudyList.size(); i++) {
			temp = myStudyList.get(i);
			if ("Y".equals(temp.getDeactivation())) {
				closedStudy++;
			}
		}
		
		String myImg = sgs3.getMyProfileImg(cvo.getId());
		
		model.addAttribute("myImg", myImg);
		model.addAttribute("myStudyList", myStudyList);
		model.addAttribute("appliedList", appliedList);
		model.addAttribute("total",myStudyList.size()+appliedList.size());
		
		model.addAttribute("pendingStudy", appliedList.size());
		model.addAttribute("closedStudy", closedStudy);
		model.addAttribute("openStudy", myStudyList.size()-closedStudy);
		
		// 선택 고정
		model.addAttribute("selectedLoc", cvo.getLoc());
		model.addAttribute("selectedCategory", cvo.getCategory());
		
		return "study_group/my_study";
	}//myStudyPage

	@RequestMapping(value="/study_group/study_i_made.do", method= { GET, POST })
	public String studyImadePage(HttpSession session, ConditionVO cvo, Model model) {

		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		}
		
		cvo.setId((String)session.getAttribute("id"));
		
		if (cvo.getCategory() == null) {
			cvo.setCategory("none");
		}
		if (cvo.getLoc() == null) {
			cvo.setLoc("none");
		}
		
		List<StudyIMade> list = sgs3.getStudyIMade(cvo);

		StudyIMade temp = null;
		int pendingStudy = 0;
		int closedStudy = 0;
		int openStudy = 0;
		
		for(int i=0; i<list.size(); i++) {
			temp = list.get(i);
			if ("N".equals(temp.getAccept_flag())) {
				pendingStudy++;
			}
			if ("Y".equals(temp.getAccept_flag()) && "N".equals(temp.getDeactivation())) {
				openStudy++;
			}
			if ("Y".equals(temp.getDeactivation())) {
				closedStudy++;
			}
		}
		
		String myImg = sgs3.getMyProfileImg(cvo.getId());
		
		model.addAttribute("myImg", myImg);
		model.addAttribute("studyIMadeList", list);
		model.addAttribute("pendingStudy", pendingStudy);
		model.addAttribute("closedStudy", closedStudy);
		model.addAttribute("openStudy", openStudy);
		model.addAttribute("total",list.size());
		
		// 선택 고정
		model.addAttribute("selectedLoc", cvo.getLoc());
		model.addAttribute("selectedCategory", cvo.getCategory());
		
		return "study_group/study_i_made";
	}
}//class

















