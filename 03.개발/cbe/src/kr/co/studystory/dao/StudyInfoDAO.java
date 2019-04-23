package kr.co.studystory.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.co.studystory.domain.StudyCommentDomain;
import kr.co.studystory.domain.StudyInfoDomain;
import kr.co.studystory.domain.ThumbnailDomain;

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
	 * ���͵��� �� ������ ��ȸ�ϴ� �޼���. - ���� �ʿ��� ���� ����...
	 * 
	 * @param id
	 * @return StudyInfoDomain
	 */
	public StudyInfoDomain selectStudyInfo(String s_num) {
		StudyInfoDomain s_info = null;
		SqlSession ss = getSessionFatory().openSession();
		s_info = ss.selectOne("selectDetailStudy", s_num);
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

	////////////////// ���� �׽�Ʈ
	public static void main(String[] args) {
		List<ThumbnailDomain> list = null;
		list = StudyInfoDAO.getInstance().selectThumbnailList();
		System.out.println(list);
	}// main

}// class
