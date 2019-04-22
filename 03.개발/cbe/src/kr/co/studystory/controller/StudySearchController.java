package kr.co.studystory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.studystory.domain.ThumbnailDomain;
import kr.co.studystory.service.StudyInfoService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

/**
 * 
 * ���� ������, �˻� ���, ���� �����ϴ� ���͵� �������� �̵��ϴ� Controller Ŭ����.
 * 
 * @author ����
 *
 */
@Controller
public class StudySearchController {

	@RequestMapping(value = "/main/main.do", method = GET)
	public String mainPage(Model model) {

		StudyInfoService sis = new StudyInfoService();
		// ����� ����Ʈ ����.
		List<ThumbnailDomain> list = sis.getThumbnailList();
		// model ��ü�� �� ����.
		model.addAttribute("thumbnail_list", list);

		return "study_info/main";
	}// mainPage

	/**
	 * ���� �˻�.
	 * @return
	 */
	@RequestMapping(value = "search/search.do", method = GET)
	public String conditionSearchPage() {
		
		return "study_info/search";
	}// conditionSearchPage

	public String likeOrDislikeProcess() {

		return "";
	}// likeOrDislikeProcess

	public String WordSearchedPage() {

		return "";
	}// WordSearchedPage


	public String studyLikedPage() {

		return "";
	}// studyLikedPage

}// class
