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
import kr.co.studystory.vo.FavFlagVO;
import kr.co.studystory.vo.FavSNumFlagVO;
import kr.co.studystory.vo.FavStudyOrderVO;
import kr.co.studystory.vo.MainFavListVO;
import kr.co.studystory.vo.MainLatestListVO;
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
	public String mainPage(MainFavListVO mfl_vo, MainLatestListVO mll_vo, Model model, HttpSession session) {

		// ������ ���� �Ǿ��ٸ� �α��� ��������.
		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		} // end if

		if (mfl_vo.getFavStartNum() == 0) {
			mfl_vo.setFavStartNum(1);
		} // end if

		if (mfl_vo.getFavEndNum() == 0) {
			mfl_vo.setFavEndNum(4);
		} // end if

		if (mll_vo.getLatestStartNum() == 0) {
			mll_vo.setLatestStartNum(1);
		} // end if

		if (mll_vo.getLatestEndNum() == 0) {
			mll_vo.setLatestEndNum(4);
		} // end if

		// ����� ����Ʈ ����.
		List<ThumbnailDomain> favList = sis.getFavThList(mfl_vo);
		List<ThumbnailDomain> latestList = sis.getLatestThList(mll_vo);

		System.out.println("////////////////////////// ��Ʈ�� : " + favList);
		System.out.println("////////////////////////// ��Ʈ�� : " + latestList);

		// model ��ü�� �� ����.
		model.addAttribute("favList", favList);
		model.addAttribute("favCurPage", 1);
		model.addAttribute("latestList", latestList);
		model.addAttribute("latestCurPage", 1);

		return "study_info/main";
	}// mainPage

	//////////////////////////////////////////////////////////////////////////////////

	/**
	 * ���� �������� ��ûó��.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/mainProcess/mainProcess.do", method = GET, produces = "application/text; charset=utf8")
	public String mainProcess(MainFavListVO mfl_vo, MainLatestListVO mll_vo, HttpSession session, Model model) {
		JSONObject json = null;

		// ������ ���� �Ǿ��ٸ� �α��� ��������.
		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		} // end if

		// �α� ���͵�κ����� ��û�� ���.
		if (mll_vo.getLatestCurPage() == 0 && mfl_vo.getFavCurPage() != 0) {
			int startNum = sis.mainStartNum(mfl_vo.getFavCurPage()); // DB���� ��ȸ�� ���� �������� �Խù��� ���� ��ȣ.
			int endNum = sis.mainEndNum(startNum); // ���� �������� �Խù��� �� ��ȣ

			// ��û���� ���� ������ ���� VO�� ������.
			mfl_vo.setFavStartNum(startNum); // ������ ���� ��ȸ�� ���� ��ȣ
			mfl_vo.setFavEndNum(endNum);// ������ ���� ��ȸ�� �� ��ȣ.

			// JSON ���.
			json = sis.getMainFavListProcess(mfl_vo);
		} // end if

		// �ֽ� ���͵�� ���� ��û�� ���.
		if (mfl_vo.getFavCurPage() == 0 && mll_vo.getLatestCurPage() != 0) {
			int startNum = sis.mainStartNum(mll_vo.getLatestCurPage()); // DB���� ��ȸ�� ���� �������� �Խù��� ���� ��ȣ.
			int endNum = sis.mainEndNum(startNum); // ���� �������� �Խù��� �� ��ȣ

			System.out.println("/////////////////////////////// ��Ʈ��" + startNum + "/ /" + endNum);

			// ��û���� ���� ������ ���� VO�� ������.
			mll_vo.setLatestStartNum(startNum); // ������ ���� ��ȸ�� ���� ��ȣ
			mll_vo.setLatestEndNum(endNum);
			;// ������ ���� ��ȸ�� �� ��ȣ.

			// JSON ���.
			json = sis.getMainLatestListProcess(mll_vo);
		} // end if

		return json.toJSONString();
	} // mainAjax

	//////////////////////////////////////////////////////////////////////////////////

	/**
	 * �� ���� ���͵����� ������ ��û ó��.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/interest/show_interest_study.do", method = GET)
	public String studyLikedPage(FavStudyOrderVO fso_vo, Model model, HttpSession session) {

		// ������ ���� �Ǿ��ٸ� �α��� ��������.
		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		} // end if

		String my_id = (String) session.getAttribute("id"); // �� ���̵� ���.
		fso_vo.setMy_id(my_id);

		// ���� ȣ��� �ʱ�ȭ�� ���� �������� 1�������� ����.
		if (fso_vo.getCurrentPage() == 0) {
			fso_vo.setCurrentPage(1);
		} // end if

		if (fso_vo.getFav_order_select() == null) {
			fso_vo.setFav_order_select("none");
		} // end if

		if (fso_vo.getFav_loc_select() == null) {
			fso_vo.setFav_loc_select("none");
		} // end if

		if (fso_vo.getFav_kind_select() == null) {
			fso_vo.setFav_kind_select("none");
		} // end if

		int totalCnt = sis.getFavStudyCnt(fso_vo);
		int totalPage = sis.totalPage(totalCnt); // ��ü �Խù��� �����ֱ� ���� �� �������� ��.
		int startNum = sis.startNum(fso_vo.getCurrentPage()); // DB���� ��ȸ�� ���� �������� �Խù��� ���� ��ȣ.
		int endNum = sis.endNum(startNum); // ���� �������� �Խù��� �� ��ȣ

		int pageIndexNum = sis.pageIndexNum(); // �� ȭ�鿡 ������ �������� �ε��� �� ���. - 3��.
		int startPage = sis.startPage(fso_vo.getCurrentPage(), pageIndexNum); // ������ �ε����� ù ��ȣ.
		int endPage = sis.endPage(startPage, pageIndexNum, totalPage); // ������ �ε����� �� ��ȣ.

		// ��û���� ���� ������ ���� VO�� ������.
		fso_vo.setStartNum(startNum); // ������ ���� ��ȸ�� ���� ��ȣ
		fso_vo.setEndNum(endNum); // ������ ���� ��ȸ�� �� ��ȣ.
		List<ThumbnailDomain> list = sis.getMyFavStudy(fso_vo);

		// ���������̼��� URL�� ���� �ϱ� ���� ���ڿ�
		String responseURL = "../interest/show_interest_study.do?fav_order_select=" + fso_vo.getFav_order_select() + "&fav_loc_select=" + fso_vo.getFav_loc_select() + "&fav_kind_select=" + fso_vo.getFav_kind_select();

		// ����� ����Ʈ ����.

		// model ��ü�� �� ����.
		model.addAttribute("thumbnail_list", list);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("responseURL", responseURL);
		model.addAttribute("currentPage", fso_vo.getCurrentPage());
		model.addAttribute("pageIndexNum", pageIndexNum);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "study_info/show_interest_study";
	}// studyLikedPage

	/**
	 * ���ƿ�� üũ�ϱ�.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/heartProcess/heartProcess.do", method = GET)
	public String heartProcess(FavFlagVO ff_vo, HttpSession session) {

		// ������ ���� �Ǿ��ٸ� �α��� ��������.
		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		} // end if

		JSONObject json = null;
		String id = (String) session.getAttribute("id");

		System.out.println("///////////////////// ��Ʈ�� : " + ff_vo.getsNum() + " / " + ff_vo.getColor() + " / " + ff_vo.getMy_id());

		// vo�� ���̵� �����ϱ�.
		if (ff_vo.getMy_id() == null) {
			ff_vo.setMy_id(id);
		} // end if

		System.out.println("///////////////////// ��Ʈ�� - ���̽� �ֱ� ���� : " + ff_vo.getsNum() + " / " + ff_vo.getColor() + " / " + ff_vo.getMy_id());
		json = sis.heartProcess(ff_vo);

		return json.toJSONString();
	}// heartProcess

	/**
	 * ���͵� ã��� ������ ��û ó��.
	 * 
	 * @param sl_vo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search/search.do", method = GET)
	public String searchStudy(SearchListVO sl_vo, FavSNumFlagVO fsf_vo, Model model, HttpSession session) {

		// ������ ���� �Ǿ��ٸ� �α��� ��������.
		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		} // end if

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
		int totalPage = sis.totalPage(totalCnt); // ��ü �Խù��� �����ֱ� ���� �� �������� ��. - 6��.
		int startNum = sis.startNum(sl_vo.getCurrentPage()); // DB���� ��ȸ�� ���� �������� �Խù��� ���� ��ȣ.
		int endNum = sis.endNum(startNum); // ���� �������� �Խù��� �� ��ȣ

		int pageIndexNum = sis.pageIndexNum(); // �� ȭ�鿡 ������ �������� �ε��� �� ���. - 3��.
		int startPage = sis.startPage(sl_vo.getCurrentPage(), pageIndexNum); // ������ �ε����� ù ��ȣ.
		int endPage = sis.endPage(startPage, pageIndexNum, totalPage); // ������ �ε����� �� ��ȣ.

		// ��û���� ���� ������ ���� VO�� ������.
		sl_vo.setStartNum(startNum); // ������ ���� ��ȸ�� ���� ��ȣ
		sl_vo.setEndNum(endNum); // ������ ���� ��ȸ�� �� ��ȣ.
		List<ThumbnailDomain> list = sis.getSearchList(sl_vo, fsf_vo);

		// ���������̼��� URL�� ���� �ϱ� ���� ���ڿ�
		String responseURL = "";
		if ("".equals(sl_vo.getSearch_inputBox())) { // �˻�â�� �̿����� �ʾ��� ��.
			responseURL = "../search/search.do?order_select=" + sl_vo.getOrder_select() + "&loc_select=" + sl_vo.getLoc_select() + "&kind_select=" + sl_vo.getKind_select();
		} // end if

		if (!"".equals(sl_vo.getSearch_inputBox())) {
			responseURL = "search.do?order_select=" + sl_vo.getOrder_select() + "&loc_select=" + sl_vo.getLoc_select() + "&kind_select=" + sl_vo.getKind_select() + "&search_inputBox=" + sl_vo.getSearch_inputBox();
		} // end if

		// model ��ü�� �� ����.
		model.addAttribute("thumbnail_list", list);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("responseURL", responseURL);
		model.addAttribute("currentPage", sl_vo.getCurrentPage());
		model.addAttribute("inputWord", sl_vo.getSearch_inputBox());
		model.addAttribute("pageIndexNum", pageIndexNum);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "study_info/search";
	}// searchStudy

}// class
