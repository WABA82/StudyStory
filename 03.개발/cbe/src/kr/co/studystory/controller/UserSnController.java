package kr.co.studystory.controller;
////������������ ��Ʈ�ѷ� ����

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class UserSnController {
	
	@RequestMapping(value="/study_notice/notice_list.do",method=GET)
	public  String userSnList(String s, Model m) {
		return "study_notice/notice_list";
	}
	
	
}
