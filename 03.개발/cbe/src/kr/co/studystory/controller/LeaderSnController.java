package kr.co.studystory.controller;
//���� �������� ��Ʈ�ѷ� ����

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.studystory.service.StudyNoticeService;

@Controller
public class LeaderSnController {
	@Autowired
	private StudyNoticeService sns;
	
	@RequestMapping(value="/study_notice/notice_list_leader.do", method=RequestMethod.GET)
	public String leaderSnList(String s, Model m) {
		
		
		return "study_notice/notice_list_leader";
	}//leaderSnList
	
}//class
