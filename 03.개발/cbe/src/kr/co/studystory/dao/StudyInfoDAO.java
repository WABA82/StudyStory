package kr.co.studystory.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.co.studystory.domain.LeaderOfJoinDomain;
import kr.co.studystory.domain.StudyCommentDomain;
import kr.co.studystory.domain.StudyInfoDomain;
import kr.co.studystory.domain.ThumbnailDomain;
import kr.co.studystory.vo.JoinAlarmVO;
import kr.co.studystory.vo.JoinFormVO;
import kr.co.studystory.vo.ReplyVO;

/**
 * study_info�� ���� DAO.
 * 
 * @author ����
 *
 */
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
	 * ���͵��� �� ������ ��ȸ�ϴ� �޼���.
	 * ���ϼ��� by ����(190502)
	 * @param id
	 * @return StudyInfoDomain
	 */
	public StudyInfoDomain selectStudyInfo(String s_num) {
		StudyInfoDomain s_info = null;
		SqlSession ss = getSessionFatory().openSession();
		s_info = ss.selectOne("selectDetailStudy", s_num);
		s_info.setFavNum(ss.selectOne("selectFavNum", s_num));
		s_info.setMemberNum(ss.selectOne("selectMemberNum",s_num));
		ss.close();
		return s_info;
	}// selectStudyInfo

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
	public int insertJoinForm(JoinFormVO jf_vo, JoinAlarmVO ja_vo) {
		int i_cnt = 0; // ���������� 1 ���� �߰� �� ���� ������ ����

		SqlSession ss = getSessionFatory().openSession();
		i_cnt = ss.insert("insertJoinFormVO", jf_vo);

		if (i_cnt == 1) {

			ja_vo.setContent(ja_vo.getStudyName() + "�� ������û�� �ֽ��ϴ�.");
			i_cnt = i_cnt + (ss.insert("insertJoinAlarm", ja_vo));

			if (i_cnt == 2) {
				ss.commit();
			} // end if

		} // end if

		return i_cnt;
	}// insertJoin

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

	public List<ThumbnailDomain> selectThumbLatest() {
		List<ThumbnailDomain> list = null;
		SqlSession ss = getSessionFatory().openSession();
		list = ss.selectList("selectThumbLatest");
		return list;
	}// selectThumbLatest

}// class
