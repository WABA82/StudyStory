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
import kr.co.studystory.domain.WriterInfoDomain;
import kr.co.studystory.vo.DetailMenuVO;
import kr.co.studystory.vo.FavFlagVO;
import kr.co.studystory.vo.FavSNumFlagVO;
import kr.co.studystory.vo.FavStudyOrderVO;
import kr.co.studystory.vo.JoinAlarmVO;
import kr.co.studystory.vo.JoinFormVO;
import kr.co.studystory.vo.MainFavListVO;
import kr.co.studystory.vo.ReplyVO;
import kr.co.studystory.vo.SearchListVO;

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

	//////////////////////////////////// Singleton

	/**
	 * ���͵��� �� ������ ��ȸ�ϴ� �޼���.
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
	 * ���Ե� ������� Ȯ���ϴ� �޼��� by ����
	 */
	public boolean selectAmIMember(DetailMenuVO dmvo) {
		boolean flag = false;
		SqlSession ss = getSessionFatory().openSession();
		int cnt = ss.selectOne("selectAmIMember", dmvo);
		if (cnt == 1) {
			flag = true;
		} // end if
		return flag;
	}// selectAmIMember

	/**
	 * ���Խ�û �ߴ��� Ȯ���ϴ� �޼��� by ����
	 */
	public boolean selectAmIPended(DetailMenuVO dmvo) {
		boolean flag = false;

		SqlSession ss = getSessionFatory().openSession();
		int cnt = ss.selectOne("selectAmIPended", dmvo);

		if (cnt == 1) {
			flag = true;
		}

		return flag;
	}// selectAmIPended

	/**
	 * ���͵� �������� (���������) Ȯ���ϴ��� �޼��� by ����
	 */
	public boolean selectDidIMade(DetailMenuVO dmvo) {
		boolean flag = false;

		SqlSession ss = getSessionFatory().openSession();
		int cnt = ss.selectOne("selectDidIMade", dmvo);

		if (cnt == 1) {
			flag = true;
		}

		return flag;
	}// selectDidIMade

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
	 * ����� ���� ���ϱ�.
	 * @param s_num
	 * @return
	 */
	public int selectScommentCnt(String s_num) {
		int cnt = 0;
		SqlSession ss = getSessionFatory().openSession();
		cnt = ss.selectOne("selectScommentCnt", s_num);
		return cnt;
	}// selectScommentCnt
	
	/**
	 * �� ���͵��� ����� insert�ϰ� ���� ���۽� �Է��� ������ �̹��� ���ϸ� ��ȯ �޼���.
	 * 
	 * @return int count
	 */
	public WriterInfoDomain insertComment(ReplyVO r_vo) {
		WriterInfoDomain wid = null;
		int cnt = 0;
		SqlSession ss = getSessionFatory().openSession();
		cnt = ss.insert("insertComment", r_vo);
		// 1�� ���� ���������� �Է� �Ǿ��� ��.
		if (cnt == 1) {
			wid = ss.selectOne("selectWriterInfo", r_vo);
			ss.commit();
		} // end if
		ss.close();
		return wid;
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
		JoinAlarmVO ja_vo = new JoinAlarmVO("���͵�", "���͵� ��û�Ϸ�", "\"" + jf_vo.getStudyName() + "\"�� ���� ��û�� �Ϸ�Ǿ����ϴ�.\n���� ��û�� �����Ǹ� �˸����� �˷��帮�ڽ��ϴ�.", jf_vo.getJoinerId());
		String joinerNick = ss.selectOne("selectJoinerNick", jf_vo.getJoinerId());
		JoinAlarmVO ja_vo2 = new JoinAlarmVO("���͵�", "���͵� ���Կ�û", "\"" + joinerNick + "\"�Բ��� " + jf_vo.getStudyName() + "�� ���� ��û�� �ϼ̽��ϴ�.", jf_vo.getLeaderNick());
		int cnt = ss.insert("insertJoinFormVO", jf_vo);
		cnt += ss.insert("insertJoinerAlarm", ja_vo);
		cnt += ss.insert("insertLeaderAlarm", ja_vo2);
		if (cnt == 3) {
			ss.commit();
			flag = true;
		} // end if
		ss.close();
		return flag;
	}// insertJoin

	/************************************************ ���� / ����� / �˻�. **/

	/**
	 * ���� �������� ����� ����Ʈ ��ȸ �ϴ� �޼���.
	 * 
	 * @return List<ThumbnailDomain>
	 */
	public List<ThumbnailDomain> selectFavThList(MainFavListVO mfl_vo) {
		List<ThumbnailDomain> list = null;
		SqlSession ss = getSessionFatory().openSession();
		list = ss.selectList("selectFavThList", mfl_vo);
		ss.close();
		return list;
	}// selectHotStudies()

	/**
	 * ���� ���ƿ��� ����� ��ȣ ��ȸ.
	 * 
	 * @param my_id
	 * @return
	 */
	public boolean selectMyFavSNum(FavSNumFlagVO fsf_vo) {
		boolean flag = false;
		int cnt = 0;
		SqlSession ss = getSessionFatory().openSession();
		cnt = ss.selectOne("selectMyFavSNumCNT", fsf_vo);
		System.out.println("///////////////////////// dao : " + cnt);
		if (cnt == 1) {
			flag = true;
		} // end if
		ss.close();
		return flag;
	}// selectMyFavSNum

	/**
	 * ���� ���͵� ������� ���� ��ȸ.
	 * 
	 * @return
	 */
	public int selectFavStudyCnt(FavStudyOrderVO fso_vo) {
		int cnt = 0;
		SqlSession ss = getSessionFatory().openSession();
		cnt = ss.selectOne("selectFavStudyCnt", fso_vo);
		ss.close();
		return cnt;
	}// selectTotalThumbCnt

	/**
	 * �� ���� ���͵� ����� ��ȸ.
	 * 
	 * @param my_id
	 * @return List<ThumbnailDomain>
	 */
	public List<ThumbnailDomain> selectMyFavStudy(FavStudyOrderVO fso_vo) {
		List<ThumbnailDomain> list = null;
		SqlSession ss = getSessionFatory().openSession();
		list = ss.selectList("selectFavStudy", fso_vo);
		ss.close();
		return list;
	}// selectMyFavStudy

	/////////////////////////////////////// ��Ʈ ����
	
	/**
	 * ���� ���͵�� �μ�Ʈ.
	 * 
	 * @param afs_vo
	 * @return
	 */
	public int insertFavStudy(FavFlagVO ff_vo) {
		int resultCnt = 0;
		SqlSession ss = getSessionFatory().openSession();
		System.out.println("///////////////////// �ٿ�" + ff_vo.getsNum() + " / " + ff_vo.getColor() + " / " + ff_vo.getMy_id());

		resultCnt = ss.insert("insertFavStudy", ff_vo);
		// ���������� �߰��� �Ǿ��� ��� Ŀ���ϱ�.
		if (resultCnt == 1) {
			ss.commit();
		} // end if
		ss.close();
		return resultCnt;
	}// insertFavStudy

	/**
	 * �� ���� ���͵� ����.
	 * 
	 * @param sNum
	 */
	public int deleteFavStudy(FavFlagVO ff_vo) {
		int resultCnt = 0;
		SqlSession ss = getSessionFatory().openSession();
		System.out.println("///////////////////// �ٿ�" + ff_vo.getsNum() + " / " + ff_vo.getColor() + " / " + ff_vo.getMy_id());

		resultCnt = ss.delete("deleteFavStudy", ff_vo);
		if (resultCnt == 1) {
			ss.commit();
		} // end if
		ss.close();
		return resultCnt;
	}// deleteFavStudy
	
	/////////////////////////////////////// ��Ʈ ����


	/////////////////////////// ���͵� ã��

	/**
	 * �˻��Ǵ� ������� ���� ��ȸ.
	 * 
	 * @return
	 */
	public int selectSearchListCnt(SearchListVO sl_vo) {
		int cnt = 0;
		SqlSession ss = getSessionFatory().openSession();
		cnt = ss.selectOne("selectSearchListCnt", sl_vo);
		ss.close();
		return cnt;
	}// selectTotalThumbCnt

	/**
	 * �˻��Ǵ� ����� ��ȸ.
	 * 
	 * @param sl_vo
	 * @return
	 */
	public List<ThumbnailDomain> selectSearchList(SearchListVO sl_vo) {
		List<ThumbnailDomain> list = null;
		SqlSession ss = getSessionFatory().openSession();
		list = ss.selectList("selectSearchList", sl_vo);
		ss.close();
		return list;
	}// selectSearchList

	/////////////////////////// ���͵� ã��

}// class
