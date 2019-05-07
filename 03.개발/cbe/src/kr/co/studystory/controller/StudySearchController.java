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
import kr.co.studystory.vo.SearchSelectVO;

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
	public String mainPage(Model model, HttpSession session) {

		if (session.getAttribute("id") == null) {
			return "redirect:../index.do";
		}
		
		// ����� ����Ʈ ����.
		List<ThumbnailDomain> list = sis.getThumbnailList();
		// model ��ü�� �� ����.
		model.addAttribute("thumbnail_list", list);

		return "study_info/main";
	}// mainPage

	/**
	 * ���͵� ã������ ������ ��û ó��.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search/search.do", method = GET)
	public String conditionSearchPage(Model model) {

		// ����� ����Ʈ ����.
		List<ThumbnailDomain> list = sis.getThumbnailList();
		// model ��ü�� �� ����.
		model.addAttribute("thumbnail_list", list);

		return "study_info/search";
	}// conditionSearchPage

	/**
	 * �� ���� ���͵����� ������ ��û ó��.
	 * 
	 * @return
	 */
	@RequestMapping(value = "study_info/show_interest_study.do", method = GET)
	public String studyLikedPage(Model model, HttpSession session) {
		String my_id = (String) session.getAttribute("id");
		// ����� ����Ʈ ����.
		List<ThumbnailDomain> list = sis.getMyInterestStudy(my_id);
		// model ��ü�� �� ����.
		model.addAttribute("thumbnail_list", list);
		return "study_info/show_interest_study";
	}// studyLikedPage

	public String likeOrDislikeProcess() {

		return "";
	}// likeOrDislikeProcess

	/**
	 * ���͵� ã�� ���������� �������� ������ ��û ó��.
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search/search_order_process.do", method = GET)
	public String searchOrderProccess(String order) {
		JSONObject json = null;
		json = sis.getOrderedList(order);
		return json.toJSONString();
	}// searchOrderProccess

	/**
	 * �˻� ���������� ���� �˻��� ���� ��û ó��.
	 * 
	 * @param ss_vo
	 * @return
	 */
	@RequestMapping(value = "/search/search_process.do", method = GET)
	public String searchBtnProcess(SearchSelectVO ss_vo, Model model) {
		List<ThumbnailDomain> list = sis.getConditionalThumbList(ss_vo);
		model.addAttribute("thumbnail_list", list);
		return "study_info/search";
	}// searchBtnProcess

	/////////////////////////////////////////// ���� ���� �ؾ� �� �κ�.

	public String WordSearchedPage() {
		return "";
	}// WordSearchedPage

}// class
