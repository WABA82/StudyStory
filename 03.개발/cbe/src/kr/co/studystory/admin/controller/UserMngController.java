package kr.co.studystory.admin.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import kr.co.studystory.admin.domain.DetailUser;
import kr.co.studystory.admin.domain.UserAndStudy;
import kr.co.studystory.admin.domain.UserInfo;
import kr.co.studystory.admin.service.CommonMngService;
import kr.co.studystory.admin.service.StudyAndUserService;
import kr.co.studystory.admin.vo.AlarmVO;
import kr.co.studystory.admin.vo.DetailUserVO;
import kr.co.studystory.admin.vo.UserBoardVO;
import kr.co.studystory.admin.vo.UserDetailVO;
import kr.co.studystory.service.StudyGroupService;
import kr.co.studystory.util.ShaUtil;
import kr.co.studystory.vo.CloseVO;

@SessionAttributes({"loginSession","weekUser","weekStudy","allUser","allStudy","activeFlag"})
@Controller
public class UserMngController {
	
	@Autowired
	private CommonMngService cms;
	@Autowired
	private StudyAndUserService saus;
	@Autowired
	private StudyGroupService sgs;
	
	
	/** ���� ������ ��ȸ�ؼ� ����
	 * @param uvo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/admin/user_mng.do", method= GET)
	private String userMngPage(UserBoardVO ub_vo,Model model) {
		int totalCount=cms.uTotalCount(ub_vo);
		int pageScale=cms.pageScale();
		int totalPage=cms.totalPage(totalCount);
		if(ub_vo.getCurrPage()==0) {
			ub_vo.setCurrPage(1);
		}
		
		int startNum= cms.startNum(ub_vo.getCurrPage());
		int endNum= cms.endNum(startNum);
		
		int pageIndexNum= cms.pageIndexNum();
		int startPage= cms.startPage(ub_vo.getCurrPage(), pageIndexNum);
		int endPage= cms.endPage(startPage, pageIndexNum, totalPage);
		
		UserAndStudy uas= new UserAndStudy();
		uas=cms.getCountUserAndStudy();
		int weekUser= uas.getWeekUser();
		int weekStudy= uas.getWeekStudy();
		int allUser= uas.getAllUser();
		int allStudy= uas.getAllStudy();
		model.addAttribute("weekUser",weekUser);
		model.addAttribute("weekStudy",weekStudy);
		model.addAttribute("allUser",allUser);
		model.addAttribute("allStudy",allStudy);
		
		
		ub_vo.setBegin(startNum);
		ub_vo.setEnd(endNum);
		List<UserInfo> list= saus.searchUserInfo(ub_vo);
		
		model.addAttribute("forwardFlag", false);
		model.addAttribute("backwardFlag", false);
		model.addAttribute("pageIndexNum", pageIndexNum);
		model.addAttribute("searchCondition", ub_vo.getSearchCondition());
		model.addAttribute("searchWord", ub_vo.getSearchWord());
		
		
		if(ub_vo.getCurrPage()> pageIndexNum) {
			model.addAttribute("forwardFlag", true);
		}
		
		if(totalPage> endPage) {
			model.addAttribute("backwardFlag", true);
		}
		
		model.addAttribute("uList",list);
		model.addAttribute("pageScale",pageScale);
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("currPage",ub_vo.getCurrPage());
		model.addAttribute("startPage",startPage);
		model.addAttribute("endPage",endPage);
		model.addAttribute("activeFlag","uActiveFlag");
		
		return "/admin/user_mng";
	}
	
	/**���� detail���� ����
	 * @param ud_vo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/admin/user_detail.do",method= {GET,POST})
	public String userDetailPage(UserDetailVO ud_vo, Model model) {
		DetailUser du= saus.searchDetailUser(ud_vo.getId());
		if("1".equals(du.getQuestion())) {
			du.setQuestion("�ʵ��б� ���Ӽ����� ������?");
		}else if("2".equals(du.getQuestion())) {
			du.setQuestion("���� �����ϴ� ĳ���� �̸���?");
		}
		model.addAttribute("name",du.getName());
		model.addAttribute("tel", du.getTel());
		model.addAttribute("zipcode",du.getZipcode());
		model.addAttribute("addr1",du.getAddr1());
		model.addAttribute("addr2",du.getAddr2());
		model.addAttribute("question",du.getQuestion());
		model.addAttribute("answer",du.getAnswer());
		
		return "/admin/detail_member_info";
	}
	
	/**
	 * ���� ���� ����
	 * @param du_vo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/admin/user_modify.do", method=POST)
	public String modifyUserProcess(DetailUserVO du_vo, Model model) {
		du_vo.setPass(ShaUtil.shaEncoding(du_vo.getPass()));
		boolean modifyFlag= saus.modifyUser(du_vo);
		model.addAttribute("modifyFlag", modifyFlag);

		return "forward:user_detail.do";
	}
	
	/**
	 * ����Ż��
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/admin/user_delete.do", method=GET)
	public String removeUserProcess(String id, Model model) {
		AlarmVO al_vo= new AlarmVO();
		List<String> listSnum= saus.searchDeleteSnum(id);
		boolean removeFlag= saus.removeUser(id);
		CloseVO c_vo= new CloseVO();
		c_vo.setId(id);
		if(listSnum.size()!=0) {
			for(int j=0; j<listSnum.size();j++) {
				c_vo.setsNum(listSnum.get(j));
				sgs.closeStudy(c_vo);
				List<String> listUser=saus.getMember(listSnum.get(j));
					for(int i=0; i<listUser.size();i++) {
						al_vo.setId(listUser.get(i));
						al_vo.setCategory("���͵�");
						al_vo.setSubject("���͵� ���� �Ǿ����ϴ�.");
						al_vo.setContent("["+sgs.getStudyName(listSnum.get(j))+"]  �� �ش� ������ Ȱ�� ���� �Ǿ����ϴ�. ����: [�����ڿ� ���� Ż��� ������ ���͵� �ߴ��մϴ�.]");
						cms.sendAlarm(al_vo);
						
						String preImg= saus.searchPreImg(listSnum.get(j));
						File file = new File("C:/dev/StudyStory/03.����/cbe/WebContent/study_img/"+preImg);
						if(file.exists()){
							file.delete();
						}
				}
			}
		}
		
		model.addAttribute("removeFlag", removeFlag);
		return "forward:user_mng.do";
	}
	
	
}

