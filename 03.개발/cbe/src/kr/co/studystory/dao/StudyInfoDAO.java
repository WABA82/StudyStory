package kr.co.studystory.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

import kr.co.studystory.domain.LeaderOfJoinDomain;
import kr.co.studystory.domain.StudyCommentDomain;
import kr.co.studystory.domain.StudyInfoDomain;
import kr.co.studystory.domain.ThumbnailDomain;
import kr.co.studystory.vo.DetailMenuVO;
import kr.co.studystory.vo.JoinAlarmVO;
import kr.co.studystory.vo.JoinFormVO;
import kr.co.studystory.vo.ReplyVO;
import kr.co.studystory.vo.SearchSelectVO;

/**
 * study_info�� ���� DAO.
 * 
 * @author ����
 *
 */
@Component
public class StudyInfoDAO {

	private static StudyInfoDAO si_dao;
	private SqlSessionFactory ssf = null;

	private StudyInfoDAO() {
	}// ������.

	////////////////// Singleton

	public static StudyInfoDAO getInstance() {

		if (si_dao == null) {
			si_dao = new StudyInfoDAO();
		} // end if

		return si_dao;
	}// getInstance

	/**
	 * Singleton���� ������ SqlSessionFactory ��ȯ �޼���.
	 * 
	 * @return SqlSessionFactory
	 */
	public synchronized SqlSessionFactory getSessionFatory() {

		if (ssf == null) {
			org.apache.ibatis.logging.LogFactory.useLog4JLogging();
			Reader reader = null;
			try {
				// 1.������ xml �ε�.
				reader = Resources.getResourceAsReader("kr/co/studystory/dao/mybatis_config.xml");
				// 2. MyBatis Framework ����.
				SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
				// 3. DB�� ������ ��ü �ޱ�.
				ssf = ssfb.build(reader);
				// 4. Reader �������
				if (reader != null) {
					reader.close();
				} // end if
			} catch (IOException ie) {
				ie.printStackTrace();
			} // end catch
		} // end if

		return ssf;
	}// getSessionFatory

	////////////////// Singleton

	/**
	 * ���͵��� �� ������ ��ȸ�ϴ� �޼���. - ���� �ʿ��� ���� ����...
	 * 
	 * @param id
	 * @return StudyInfoDomain
	 */
	public StudyInfoDomain selectStudyInfo(String s_num) {
		StudyInfoDomain s_info = null;
		SqlSession ss = getSessionFatory().openSession();
		s_info = ss.selectOne("selectDetailStudy", s_num);
		s_info.setFavNum(ss.selectOne("selectFavNum", s_num));
		s_info.setMemberNum(ss.selectOne("selectMemberNum", s_num));
		ss.close();
		return s_info;
	}// selectStudyInfo
	
	/**
	 * ���Ե� ������� Ȯ���ϴ� �޼���
	 * by ����
	 */
	public boolean selectAmIMember(DetailMenuVO dmvo) {
		boolean flag = false;
		
		SqlSession ss = getSessionFatory().openSession();
		int cnt = ss.selectOne("selectAmIMember",dmvo);
		if (cnt == 1) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * ���Խ�û �ߴ��� Ȯ���ϴ� �޼���
	 * by ����
	 */
	public boolean selectAmIPended(DetailMenuVO dmvo) {
		boolean flag = false;
		
		SqlSession ss = getSessionFatory().openSession();
		int cnt = ss.selectOne("selectAmIPended",dmvo);
		if (cnt == 1) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * ���͵� �������� (���������) Ȯ���ϴ��� �޼���
	 * by ����
	 */
	public boolean selectDidIMade(DetailMenuVO dmvo) {
		boolean flag = false;
		
		SqlSession ss = getSessionFatory().openSession();
		int cnt = ss.selectOne("selectDidIMade",dmvo);
		if (cnt == 1) {
			flag = true;
		}
		
		return flag;
	}
	
	

	/**
	 * �� ���͵��� ��� List�� ��ȸ�ϴ� �޼���.
	 * 
	 * @return List<StudyCommentDomain>
	 */
	public List<StudyCommentDomain> selectSCommentList(String s_num) {
		List<StudyCommentDomain> list = null;
		SqlSession ss = getSessionFatory().openSession();
		list = ss.selectList("selectStudyCommentList", s_num);
		ss.close();
		return list;
	}// selectSCommentList

	/**
	 * �� ���͵��� ����� insert�ϴ� �޼���.
	 * 
	 * @return int count
	 */
	public int insertComment(ReplyVO r_vo) {
		int cnt = 0;
		SqlSession ss = getSessionFatory().openSession();
		cnt = ss.insert("insertComment", r_vo);

		// 1�� ���� ���������� �Է� �Ǿ��� ��.
		if (cnt == 1) {
			ss.commit();
		} // end if
		ss.close();
		return cnt;
	}// insertComment

	/**
	 * ���͵� �����ϱ��� ���� ���� ��ȸ.
	 * 
	 * @return LeaderOfJoinDomain
	 */
	public LeaderOfJoinDomain selectLeaderOfJoin(String s_num) {
		LeaderOfJoinDomain loj = null;
		SqlSession ss = getSessionFatory().openSession();
		loj = ss.selectOne("selectLeaderOfJoin", s_num);
		ss.close();
		return loj;
	}// selectLeaderOfJoin

	/**
	 * ���͵� �����ϱ� insert
	 * 
	 * @return
	 */
	public boolean insertJoinForm(JoinFormVO jf_vo) {
		boolean flag = false;
		
		SqlSession ss = getSessionFatory().openSession();
		
		JoinAlarmVO ja_vo = new JoinAlarmVO("���͵�", "���͵� ��û�Ϸ�",
			"\""+jf_vo.getStudyName()+"\"�� ���� ��û�� �Ϸ�Ǿ����ϴ�.\n���� ��û�� �����Ǹ� �˸����� �˷��帮�ڽ��ϴ�.",
			jf_vo.getJoinerId());
		
		String joinerNick = ss.selectOne("selectJoinerNick", jf_vo.getJoinerId());
		
		JoinAlarmVO ja_vo2 = new JoinAlarmVO("���͵�", "���͵� ���Կ�û",
			"\""+joinerNick+"\"�Բ��� "+jf_vo.getStudyName()+"�� ���� ��û�� �ϼ̽��ϴ�.",
			jf_vo.getLeaderNick());
		
		int cnt = ss.insert("insertJoinFormVO", jf_vo);
		cnt += ss.insert("insertJoinerAlarm", ja_vo);
		cnt += ss.insert("insertLeaderAlarm", ja_vo2);
		
		if (cnt == 3) {
			ss.commit();
			flag = true;
		}
		
		ss.close();

		return flag;
	}// insertJoin

	/************************************************ ���� / ����� / �˻�. **/

	/**
	 * ���� �������� ����� ����Ʈ ��ȸ �ϴ� �޼���.
	 * 
	 * @return List<ThumbnailDomain>
	 */
	public List<ThumbnailDomain> selectThumbnailList() {
		List<ThumbnailDomain> list = null;
		SqlSession ss = getSessionFatory().openSession();
		list = ss.selectList("selectThumbnailList");
		ss.close();
		return list;
	}// selectHotStudies()

	public boolean insertLikeStudy() {
		SqlSession ss = getSessionFatory().openSession();
		ss.close();
		return false;
	}// insertLikeStudy

	public boolean deleteLikeStudy() {
		SqlSession ss = getSessionFatory().openSession();
		ss.close();
		return false;
	}// insertLikeStudy

	/**
	 * �� ���� ���͵� ����� ��ȸ.
	 * 
	 * @param my_id
	 * @return List<ThumbnailDomain>
	 */
	public List<ThumbnailDomain> selectMyFavStudy(String my_id) {
		List<ThumbnailDomain> list = null;
		SqlSession ss = getSessionFatory().openSession();
		list = ss.selectList("selectFavStudy", my_id);
		ss.close();
		return list;
	}// selectMyFavStudy

	/**
	 * �ֽż����� ����� ��ȸ.
	 * 
	 * @return
	 */
	public List<ThumbnailDomain> selectThumbLatest() {
		List<ThumbnailDomain> list = null;
		SqlSession ss = getSessionFatory().openSession();
		list = ss.selectList("selectThumbLatest");
		return list;
	}// selectThumbLatest

	/**
	 * �˻� ���ǿ� ���� ����� ��ȸ.
	 * 
	 * @return
	 */
	public List<ThumbnailDomain> selectConditionalThumbList(SearchSelectVO ss_vo) {
		List<ThumbnailDomain> list = null;
		SqlSession ss = getSessionFatory().openSession();
		list = ss.selectList("selectThumbCon", ss_vo);
		return list;
	}// selectConditionThumb

}// class
