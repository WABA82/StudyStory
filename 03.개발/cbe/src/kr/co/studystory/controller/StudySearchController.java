package kr.co.studystory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
/**
 * 
 * ���� ������, �˻� ���, ���� �����ϴ� ���͵� �������� �̵��ϴ� Controller Ŭ����.
 * 
 * @author ����
 *
 */
@Controller
public class StudySearchController {

	@RequestMapping(value="/study_info/main.do", method= { GET, POST })
	public String mainPage(Model model) {

		return "study_info/main";
	}// mainPage

	public String likeOrDislikeProcess() {

		return "";
	}// likeOrDislikeProcess

	public String WordSearchedPage() {

		return "";
	}// WordSearchedPage

	public String conditionSearchPage() {

		return "";
	}// conditionSearchPage

	public String studyLikedPage() {

		return "";
	}// studyLikedPage

}// class
