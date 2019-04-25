package kr.co.studystory.admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import kr.co.studystory.admin.domain.UserAndStudy;
import kr.co.studystory.admin.service.CommonMngService;
import kr.co.studystory.admin.vo.LoginVO;

@SessionAttributes("loginSession")
@Controller
public class adLoginController {
	@Autowired
	private CommonMngService cms;
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
	@RequestMapping(value="/admin/login_proc.do", method=RequestMethod.GET)
	public String loginProcess(LoginVO l_vo, Model model) {
		String url= "admin/login";
		boolean loginFlag=false;
		UserAndStudy uas= new UserAndStudy();
		uas=cms.getCountUserAndStudy();
		
		loginFlag= cms.login(l_vo);
		
		int weekUser= uas.getWeekUser();
		int weekStudy= uas.getWeekStudy();
		int allUser= uas.getAllUser();
		int allStudy= uas.getAllStudy();
		
		if(loginFlag) {
			model.addAttribute("loginSession",true);
			url= "redirect:/admin/new_study.do";
		}
		
		model.addAttribute("loginFlag",loginFlag);
		model.addAttribute("weekUser",weekUser);
		model.addAttribute("weekStudy",weekStudy);
		model.addAttribute("allUser",allUser);
		model.addAttribute("allStudy",allStudy);
		
		return url;
	}
	
	/**
	 * �α׾ƿ�
	 * @param ss
	 */
	@RequestMapping(value="/admin/logout.do")
	public String LogOut(SessionStatus ss,Model model){
		boolean logoutFlag=false;
		ss.setComplete();
		logoutFlag=true;
		model.addAttribute("logoutFlag",logoutFlag);
		return "admin/login";
	}
	
	
}
