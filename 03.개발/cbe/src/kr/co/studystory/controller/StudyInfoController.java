package kr.co.studystory.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.studystory.domain.LeaderOfJoinDomain;
import kr.co.studystory.domain.StudyCommentDomain;
import kr.co.studystory.domain.StudyInfoDomain;
import kr.co.studystory.service.StudyInfoService;
import kr.co.studystory.vo.JoinFormVO;
import kr.co.studystory.vo.ReplyVO;

/**
 * ���͵� ������, ���͵� ������û, �Խ��� �������� �̵��ϴ� Controller Ŭ����.
 * 
 * @author ����
 *
 */
@Controller
public class StudyInfoController {

	/**
	 * ���͵� �� ���� �������� ������ ��û ó��.
	 * 
	 * @param sNum
	 * @param session
	 * @param model
	 * @return
	 */
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
	 * ���͵� �� ���� �������� ��۷� ������ ��û ó��.
	 * 
	 * @return
	 */
	@ResponseBody // DispatcherServlet�� ��ġ�� �ʰ� �ٷ� ����.
	@RequestMapping(value = "/detail/add_reply.do", method=GET)
	public String studyInfoReply(ReplyVO r_vo, HttpSession session) {
		JSONObject json = null;
		StudyInfoService sis = new StudyInfoService();
		
		// �������� ����� �Է��ϴ� ������� ���̵� ���.
		String user_id = (String) session.getAttribute("id");
		// VO�� set�ϱ�.
		r_vo.setId(user_id);
		
		// �� Ȯ�� �ϱ�.
		System.out.println("/////////////////////////////////" + r_vo.getId() + "/" + r_vo.getReply() + "/" + r_vo.getsNum());
		
		json = sis.addReply(r_vo);
		return json.toJSONString();
	}// addComment()

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

	//////////////////////////////////////////////////////////////////// ���� ������.

	/**
	 * ���͵� ���� �������� Form���� ������ ��û ó��.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/study_info/join_process.do", method = POST)
	public String joinProcess(JoinFormVO jf_vo, HttpSession session) {

		System.out.println("/////////////////////////" + jf_vo.getIntroduce_ta() + " / " + jf_vo.getApply_ta());

		return "study_info/main"; // redirect�� �̵��ؾ� �Ѵ�.
	}// joinProcess()

}// class
