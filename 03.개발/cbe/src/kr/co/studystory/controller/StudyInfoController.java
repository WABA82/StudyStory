package kr.co.studystory.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.studystory.domain.StudyCommentDomain;
import kr.co.studystory.domain.StudyInfoDomain;
import kr.co.studystory.service.StudyInfoService;

/**
 * ���͵� ������, ���͵� ������û, �Խ��� �������� �̵��ϴ� Controller Ŭ����.
 * 
 * @author ����
 *
 */
@Controller
public class StudyInfoController {

	@RequestMapping(value = "study_info/study_info.do", method = GET)
	public String studyInfoPage(String s_num, HttpSession session, Model model) {

		StudyInfoService sis = new StudyInfoService();

		StudyInfoDomain sInfo = sis.getStudyInfo("s_000042"); // ���͵� �� ���� ��������.
		List<StudyCommentDomain> sCommentList = sis.getStudyComment("s_000042"); // ���͵� �������� ��� List ��������.

		model.addAttribute("s_Info", sInfo); // ���͵� ������ model�� ���.
		model.addAttribute("sCommentList", sCommentList); // ��� list �𵨿� ���.

		return "study_info/detailStudy";
	}// studyInfoPage()

	public String addComment() {

		return "";
	}// addComment()

	public String joinPage() {

		return "";
	}// joinPage()

	public String joinProcess() {

		return "";
	}// joinProcess()

}
