package kr.co.studystory.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.co.studystory.studyInfo.domain.ThumbnailDomain;

public class StudyInfoDAO {

	private static StudyInfoDAO si_dao;
	private SqlSessionFactory ssf = null;

	/**
	 * �̱������� Ŭ���� ������ ����.
	 */
	private StudyInfoDAO() {
	}// ������.

	public static StudyInfoDAO getInstance() {
		if (si_dao == null) {
			si_dao = new StudyInfoDAO();
		} // end if
		return si_dao;
	}// getInstance

	/**
	 * SqlSessionFactoryBuilder�� ���� ssf��ü �ޱ�.
	 * 
	 * @return SqlSessionFactory ssf
	 */
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

				if (reader != null) {
					reader.close();
				} // end if

			} catch (IOException ie) {
				ie.printStackTrace();
			} // end catch

		} // end if

		return ssf;
	}// getSessionFatory

	/////////////////////// ���� ������ ///////////////////////

	/**
	 * ���� : DB���� Thumbnail��� ��ȸ�ϴ� �޼���.
	 * 
	 * @return List<ThumbnailDomain>
	 */
	public List<ThumbnailDomain> selectHotStudies() {
		List<ThumbnailDomain> list = null;

		return list;
	}// selectHotStudies()

	/////////////////////// ���� ������ ///////////////////////

}// class
