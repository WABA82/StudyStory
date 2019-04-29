package kr.co.studystory.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.studystory.admin.dao.AdCommonDAO;
import kr.co.studystory.admin.domain.UserAndStudy;
import kr.co.studystory.admin.vo.AlarmVO;
import kr.co.studystory.admin.vo.LoginVO;
import kr.co.studystory.admin.vo.NewStudyBoardVO;
import kr.co.studystory.admin.vo.NoticeBoardVO;
import kr.co.studystory.admin.vo.QuestionBoardVO;
import kr.co.studystory.admin.vo.StudyBoardVO;
import kr.co.studystory.admin.vo.UserBoardVO;

@Component
public class CommonMngService {
	@Autowired
	private AdCommonDAO c_dao;
	
	/**
	 * �α���
	 * @param l_vo
	 * @return
	 */
	public boolean login(LoginVO l_vo) {
		boolean login_flag=c_dao.selectLogin(l_vo);
		return login_flag;
	}
	
	/**
	 * �˶�
	 * @param a_vo
	 * @return
	 */
	public boolean sendAlarm(AlarmVO a_vo) {
		boolean alramFlag=c_dao.insertAlarm(a_vo);
		return alramFlag;
	}

	
	/**
	 * �ְ��ű�,�ְ����͵�,��ȸ����,�ѽ��͵�� ��ȸ
	 * @return
	 */
	public UserAndStudy getCountUserAndStudy() {
		UserAndStudy uas= new UserAndStudy();
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
		int totalPage=c_dao.selectNewStudyTotal();
		return totalPage;
	}
	
	public int userCount(UserBoardVO ub_vo) {
		int totalPage=c_dao.selectUserTotal(ub_vo);
		return totalPage;
	}
	public int studyCount(StudyBoardVO sb_vo) {
		int totalPage=c_dao.selectStudyTotal(sb_vo);
		return totalPage;
	}
	
	public int questionCount(QuestionBoardVO qb_vo) {
		
		int totalPage=c_dao.selectQuestionTotal(qb_vo);
		return totalPage;
	}
	public int noticeCount(NoticeBoardVO nb_vo) {
		
		int totalPage=c_dao.selectNoticeTotal(nb_vo);
		return totalPage;
	}
	
	/**
	 * new study �� �Խù��� �� ���
	 * @return
	 */
	public int nsTotalCount() {
		int cnt=0;
		cnt = c_dao.selectNewStudyTotal();
		return cnt;
	}//nsTotalCount
	
	/**
	 * user �� �Խù��� �� ���
	 * @return
	 */
	public int uTotalCount(UserBoardVO ub_vo) {
		int cnt=0;
		cnt = c_dao.selectUserTotal(ub_vo);
		return cnt;
	}//nsTotalCount
	
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
	
	public int pageIndexNum() {
		return 3;
	}
	
	public int startPage(int currPage, int pageIndexNum) {
		int startPage = ((currPage - 1)/pageIndexNum) * pageIndexNum + 1;
		return startPage;
	}
	
	public int endPage(int startPage, int pageIndexNum, int totalPage) {
		int endPage = (((startPage - 1) + pageIndexNum) / pageIndexNum)*pageIndexNum;
		
		if (totalPage <= endPage) {
			endPage = totalPage;
		}
		
		return endPage;
	}
	
	
	public static void main(String[] args) {
		CommonMngService cms= new CommonMngService();
		System.out.println();
	}
}
