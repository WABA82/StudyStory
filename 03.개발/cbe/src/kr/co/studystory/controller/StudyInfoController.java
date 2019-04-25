package kr.co.studystory.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.studystory.domain.LeaderOfJoinDomain;
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

	@RequestMapping(value = "/detail/detail_study.do", method = GET)
	public String studyInfoPage(String sNum, HttpSession session, Model model) {
		StudyInfoService sis = new StudyInfoService();
		StudyInfoDomain sInfo = sis.getStudyInfo(sNum); // ���͵� �� ���� ��������.
		List<StudyCommentDomain> sCommentList = sis.getStudyComment(sNum); // ���͵� �������� ��� List ��������.
		model.addAttribute("s_Info", sInfo); // ���͵� ������ model�� ���.
		model.addAttribute("sCommentList", sCommentList); // ��� list �𵨿� ���.
		return "study_info/detailStudy";
	}// studyInfoPage()

	/**
	 * ���͵� ���� ��û ���������� ������ ��û ó��.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/study_info/study_req_join.do", method = GET)
	public String joinPage(String sNum, Model model, HttpSession session) {
		StudyInfoService sis = new StudyInfoService();
		LeaderOfJoinDomain loj = sis.getLeaderOfJoin(sNum);

		// session.getAttribute�� �Ӽ��� ���� �������� ���̴�. - sessionScope�� ����ϴ� ���� �ƴϴ�.
		String my_nick = (String) session.getAttribute("nick");

		// model�� �� ����.
		model.addAttribute("leader", loj);
		// session�� ����ϴ� ���� �ƴϱ� ������ ${ sessionScope.my_nick }���� ��� �� �� ����.
		model.addAttribute("my_nick", my_nick);

		return "study_info/study_join_req";
	}// joinPage()

	public String addComment() {

		return "";
	}// addComment()

	public String joinProcess() {

		return "";
	}// joinProcess()

}
