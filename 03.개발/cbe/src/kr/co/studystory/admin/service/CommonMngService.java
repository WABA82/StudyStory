package kr.co.studystory.admin.service;

import kr.co.studystory.admin.dao.CommonDAO;
import kr.co.studystory.admin.domain.UserAndStudy;
import kr.co.studystory.admin.vo.LoginVO;
import kr.co.studystory.admin.vo.NewStudyBoardVO;
import kr.co.studystory.admin.vo.NoticeBoardVO;
import kr.co.studystory.admin.vo.QuestionBoardVO;
import kr.co.studystory.admin.vo.StudyBoardVO;
import kr.co.studystory.admin.vo.UserBoardVO;

public class CommonMngService {
	
	/**
	 * �α���
	 * @param l_vo
	 * @return
	 */
	public boolean login(LoginVO l_vo) {
		CommonDAO c_dao= CommonDAO.getInstance();
		boolean login_flag=c_dao.selectLogin(l_vo);
		return login_flag;
	}
	
	/**
	 * �ְ��ű�,�ְ����͵�,��ȸ����,�ѽ��͵�� ��ȸ
	 * @return
	 */
	public UserAndStudy getCountUserAndStudy() {
		UserAndStudy uas= new UserAndStudy();
		CommonDAO c_dao= CommonDAO.getInstance();
		uas.setWeekUser(c_dao.selectWeekUser());
		uas.setWeekStudy(c_dao.selectWeekStudy());
		uas.setAllUser(c_dao.selectAllUser());
		uas.setAllStudy(c_dao.selectAllStudy());
		return uas;
	}
	
	
	/**
	 * new study�� totalcount ��ȸ
	 * @param nsb_vo
	 * @return
	 */
	public int newStudyCount(NewStudyBoardVO nsb_vo) {
		CommonDAO c_dao= CommonDAO.getInstance();
		int totalPage=c_dao.selectNewStudyTotal();
		return totalPage;
	}
	
	public int userCount(UserBoardVO ub_vo) {
		CommonDAO c_dao= CommonDAO.getInstance();
		int totalPage=c_dao.selectUserTotal(ub_vo);
		return totalPage;
	}
	public int studyCount(StudyBoardVO sb_vo) {
		CommonDAO c_dao= CommonDAO.getInstance();
		int totalPage=c_dao.selectStudyTotal(sb_vo);
		return totalPage;
	}
	
	public int questionCount(QuestionBoardVO qb_vo) {
		CommonDAO c_dao= CommonDAO.getInstance();
		int totalPage=c_dao.selectQuestionTotal(qb_vo);
		return totalPage;
	}
	public int noticeCount(NoticeBoardVO nb_vo) {
		CommonDAO c_dao= CommonDAO.getInstance();
		int totalPage=c_dao.selectNoticeTotal(nb_vo);
		return totalPage;
	}
	
	/**
	 * ���������� ������ �Խù��� �� 
	 * @return
	 */
	public int pageScale() {
		int pageScale=10;
		return pageScale;
	}//pageScale
	
	/**
	 * ��� �Խù��� �����ֱ� ���� ������ ��
	 * @param totalCount
	 * @return
	 */
	public int totalPage(int totalCount) {
		int totalPage= totalCount/pageScale();
		if(totalCount%pageScale()!=0){
			totalPage++;
		}//end if
		return totalPage;
	}//totalPage
	
	/**
	 * ������ �ε��� ����Ʈ���� ��ȸ�� ���� ��ȣ
	 * @param currentPage
	 * @return
	 */
	public int startNum(int currentPage) {
		int startNum=1;
		startNum= currentPage*pageScale()-pageScale()+1;
		return startNum;
	}//startNum
	
	/**
	 * ������ �ε��� ����Ʈ���� ��ȸ�� �� ��ȣ
	 * @param startNum
	 * @return
	 */
	public int endNum(int startNum) {
		int endNum=startNum+pageScale()-1;
		return endNum;
	}//endNum
	
	public static void main(String[] args) {
		CommonMngService cms= new CommonMngService();
		cms.getCountUserAndStudy();
	}
}
