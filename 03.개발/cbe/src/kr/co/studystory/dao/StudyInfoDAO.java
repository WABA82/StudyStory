package kr.co.studystory.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

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

	public static StudyInfoDAO getInstance() {
		if (si_dao == null) {
			si_dao = new StudyInfoDAO();
		} // end if
		return si_dao;
	}// getInstance

	public synchronized SqlSessionFactory getSessionFatory() {

		if (ssf == null) {
			org.apache.ibatis.logging.LogFactory.useLog4JLogging();

			Reader reader = null;
			try {
				// 1.������ xml �ε�.
				reader = Resources.getResourceAsReader("kr/co/sist/dao/mybatis_config.xml");

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

	///////// ���� ������ /////////

	/**
	 * DB���� Thumbnail��� ��ȸ�ϴ� �޼���.
	 * 
	 * @return List<ThumbnailDomain>
	 */
	public List<ThumbnailDomain> selectHotStudies() {
		List<ThumbnailDomain> list = null;

		return list;
	}// selectHotStudies()

	/**
	 * "���ƿ�" DB�� insert �޼���.
	 * 
	 * @return boolean
	 */
	public boolean insertLikeStudy() {
		return false;
	}// insertLikeStudy

	/**
	 * "���ƿ�" ���� ���� �� DB�� update �޼���.
	 * 
	 * @return boolean
	 */
	public boolean deleteLikeStudy() {
		return false;
	}// insertLikeStudy

	///////// ���� ������ /////////

	///////// �� ���͵� ������ /////////

	/**
	 * DB���� ���͵� �� ���� ��ȸ.
	 * 
	 * @param id
	 * @return StudyInfo
	 */
	public StudyInfoDomain selectStudyInfo(String id) {
		StudyInfoDomain s_info = null;
		SqlSession ss = getSessionFatory().openSession();
		s_info = ss.selectOne("", "");
		return s_info;
	}// selectStudyInfo

	///////// �� ���͵� ������ /////////

}// class
