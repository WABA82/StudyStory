package kr.co.studystory.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ���͵� ������, ���͵� ������û, �Խ��� �������� �̵��ϴ� Controller Ŭ����.
 * 
 * @author ����
 *
 */
@Controller
public class StudyInfoController {

	@RequestMapping(value="")
	public String studyInfoPage(String str, HttpSession session, Model model) {
		
		return "";
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
