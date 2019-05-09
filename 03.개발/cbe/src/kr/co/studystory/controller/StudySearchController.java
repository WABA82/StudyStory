package kr.co.studystory.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.studystory.domain.ThumbnailDomain;
import kr.co.studystory.service.StudyInfoService;
import kr.co.studystory.vo.AddFavStudyVO;
import kr.co.studystory.vo.FavSNumFlagVO;
import kr.co.studystory.vo.FavStudyOrderVO;
import kr.co.studystory.vo.RemoveFavStudyVO;
import kr.co.studystory.vo.SearchListVO;

/**
 * 
 * ���� ������, �˻� ���, ���� �����ϴ� ���͵� �������� �̵��ϴ� Controller Ŭ����.
 * 
 * @author ����
 *
 */
@Controller
public class StudySearchController {

	@Autowired
	private StudyInfoService sis;

	/**
	 * ���� ���������� ������ ��û ó��.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/study_info/main.do", method = { GET, POST })
	public String mainPage(FavSNumFlagVO fsf_vo, Model model, HttpSession session) {

		String id = (String) session.getAttribute("id");
		
		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		} // end if

		// id�� null�� ���.
		if (fsf_vo.getId() == null) {
			fsf_vo.setId(id);
		}// end if

		// ����� ����Ʈ ����.
		List<ThumbnailDomain> list = sis.getThumbnailList(fsf_vo);
		// model ��ü�� �� ����.
		model.addAttribute("thumbnail_list", list);

		return "study_info/main";
	}// mainPage

	/**
	 * �� ���� ���͵����� ������ ��û ó��.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/interest/show_interest_study.do", method = GET)
	public String studyLikedPage(FavStudyOrderVO fso_vo, Model model, HttpSession session) {

		if (fso_vo.getFav_order_select() == null) {
			fso_vo.setFav_order_select("none");
		} // end if

		if (fso_vo.getFav_loc_select() == null) {
			fso_vo.setFav_loc_select("none");
		} // end if

		if (fso_vo.getFav_kind_select() == null) {
			fso_vo.setFav_kind_select("none");
		} // end if

		String my_id = (String) session.getAttribute("id"); // �� ���̵� ���.
		fso_vo.setMy_id(my_id);

		// ����� ����Ʈ ����.
		List<ThumbnailDomain> list = sis.getMyFavStudy(fso_vo);
		// model ��ü�� �� ����.
		model.addAttribute("thumbnail_list", list);

		return "study_info/show_interest_study";
	}// studyLikedPage

	/**
	 * ���ƿ�� üũ�ϱ�.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/likeProcess/likeProcess.do", method = GET)
	public String likeProcess(AddFavStudyVO afs_vo, HttpSession session) {
		JSONObject json = new JSONObject();
		boolean resultFlag = false;

		String id = (String) session.getAttribute("id");// �������� ���̵� ���.
		afs_vo.setMy_id(id);

		resultFlag = sis.addLikeProcess(afs_vo);
		json.put("resultFlag", resultFlag);
		return json.toJSONString();
	}// likeProcess

	/**
	 * ���ƿ� ��ư�� ������ ������ ��û ó��.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/dislikeProcess/dislikeProcess.do", method = GET)
	public String dislikeProcess(RemoveFavStudyVO rfa_vo, HttpSession session) {
		JSONObject json = new JSONObject();
		boolean resultFlag = false;

		String id = (String) session.getAttribute("id");// �������� ���̵� ���.
		rfa_vo.setId(id);

		resultFlag = sis.removeLikeProcess(rfa_vo);
		json.put("resultFlag", resultFlag);
		return json.toJSONString();
	}// likeOrDislikeProcess

	/**
	 * ���͵� ã��� ������ ��û ó��.
	 * 
	 * @param sl_vo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search/search.do", method = GET)
	public String searchStudy(SearchListVO sl_vo, FavSNumFlagVO fsf_vo, Model model, HttpSession session) {

		String id = (String) session.getAttribute("id");
		fsf_vo.setId(id);
		
		System.out.println("////////////////////////��Ʈ�� : " + " ����/ " + sl_vo.getOrder_select() + " ����/ " + sl_vo.getLoc_select() + " ����/ " + sl_vo.getKind_select() + " �Է�/ " + sl_vo.getSearch_inputBox());

		// ���� ȣ��� �ʱ�ȭ�� ���� �������� 1�������� ����.
		if (sl_vo.getCurrentPage() == 0) {
			sl_vo.setCurrentPage(1);
		} // end if

		if (sl_vo.getOrder_select() == null) {
			sl_vo.setOrder_select("none");
		} // end if

		if (sl_vo.getLoc_select() == null) {
			sl_vo.setLoc_select("none");
		} // end if

		if (sl_vo.getKind_select() == null) {
			sl_vo.setKind_select("none");
		} // end if

		if (sl_vo.getSearch_inputBox() == null) {
			sl_vo.setSearch_inputBox("");
		} // end if

		int totalCnt = sis.getSearchListCnt(sl_vo); // �� �Խù��� ��.
		int totalPage = sis.totalPage(totalCnt); // ��ü �Խù��� �����ֱ� ���� �� �������� ��.
		int startNum = sis.startNum(sl_vo.getCurrentPage()); // DB���� ��ȸ�� ���� �������� �Խù��� ���� ��ȣ.
		int endNum = sis.endNum(startNum); // ���� �������� �Խù��� �� ��ȣ

		// ��û���� ���� ������ ���� VO�� ������.
		sl_vo.setStartNum(startNum); // ������ ���� ��ȸ�� ���� ��ȣ
		sl_vo.setEndNum(endNum); // ������ ���� ��ȸ�� �� ��ȣ.

		// ���������̼��� URL�� ���� �ϱ� ���� ���ڿ�
		String responseURL = "";
		if ("".equals(sl_vo.getSearch_inputBox())) { // �˻�â�� �̿����� �ʾ��� ��.
			responseURL = "../search/search.do?order_select=" + sl_vo.getOrder_select() + "&loc_select=" + sl_vo.getLoc_select() + "&kind_select=" + sl_vo.getKind_select();
		} // end if

		if (!"".equals(sl_vo.getSearch_inputBox())) {
			responseURL = "search.do?order_select=" + sl_vo.getOrder_select() + "&loc_select=" + sl_vo.getLoc_select() + "&kind_select=" + sl_vo.getKind_select() + "&search_inputBox=" + sl_vo.getSearch_inputBox();
		} // end if

		List<ThumbnailDomain> list = sis.getSearchList(sl_vo, fsf_vo);

		// model ��ü�� �� ����.
		model.addAttribute("thumbnail_list", list);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("responseURL", responseURL);
		model.addAttribute("currentPage", sl_vo.getCurrentPage());
		model.addAttribute("inputWord", sl_vo.getSearch_inputBox());

		return "study_info/search";
	}// searchStudy

}// class
