package kr.co.studystory.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import kr.co.studystory.admin.service.CommonMngService;
import kr.co.studystory.admin.vo.LoginVO;

@Controller
public class LoginController {
	
	/**
	 * �α��� ������ ����ֱ�
	 * @return
	 */
	@RequestMapping(value="/admin/login.do",method=RequestMethod.GET)
	public String loginPage() {
		return "admin/login";
	}
	
	/**
	 * �α��� ó�� (���̵�,��й�ȣ ����, ���������� ������ �� ���ϱ�)
	 * @param l_vo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/admin/login_proc.do", method=RequestMethod.POST)
	public String loginProcess(LoginVO l_vo, Model model) {
		String url= "admin/login";
		boolean loginFlag=false;
		CommonMngService cms= new CommonMngService();
		loginFlag= cms.login(l_vo);
		if(loginFlag) {
			url= "admin/new_study_mng";
		}
		model.addAttribute("loginFlag",loginFlag);
		return url;
	}
	
	/**
	 * �α׾ƿ�
	 * @param ss
	 */
	@RequestMapping(value="/admin/logout.do")
	public String LogOut(SessionStatus ss){
		ss.setComplete();
		return "admin/login";
	}
	
	
}
