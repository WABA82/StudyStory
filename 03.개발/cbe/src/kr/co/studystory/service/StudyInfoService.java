package kr.co.studystory.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.studystory.dao.StudyInfoDAO;
import kr.co.studystory.domain.LeaderOfJoinDomain;
import kr.co.studystory.domain.StudyCommentDomain;
import kr.co.studystory.domain.StudyInfoDomain;
import kr.co.studystory.domain.ThumbnailDomain;
import kr.co.studystory.vo.DetailMenuVO;
import kr.co.studystory.vo.FavFlagVO;
import kr.co.studystory.vo.FavSNumFlagVO;
import kr.co.studystory.vo.FavStudyOrderVO;
import kr.co.studystory.vo.JoinFormVO;
import kr.co.studystory.vo.ReplyVO;
import kr.co.studystory.vo.SearchListVO;

@Component
public class StudyInfoService {

	@Autowired
	private StudyInfoDAO si_dao;

	/******************* Info �������� ����. *******************/

	/**
	 * ���͵� ������ ���.
	 * 
	 * @param s_num
	 * @return StudyInfoDomain
	 */
	public StudyInfoDomain getStudyInfo(String s_num) {
		StudyInfoDomain s_info = null;
		s_info = si_dao.selectStudyInfo(s_num);
		return s_info;
	}// getStudyInfo

	/**
	 * �� ���������� ������ �������� Ȯ���ϴ� �޼���
	 */
	public boolean amIMember(DetailMenuVO dmvo) {
		return si_dao.selectAmIMember(dmvo);
	}

	/**
	 * �� ���������� ���Խ�û�� �������� Ȯ���ϴ� �޼���
	 */
	public boolean didIrequest(DetailMenuVO dmvo) {
		return si_dao.selectAmIPended(dmvo);
	}

	/**
	 * �� ���������� ���͵� ���� �������� Ȯ���ϴ� �޼���
	 */
	public boolean amILeader(DetailMenuVO dmvo) {
		return si_dao.selectDidIMade(dmvo);
	}

	/**
	 * ���͵� �� �������� ����� �Է��ϱ�.
	 * 
	 * @param reply
	 */
	@SuppressWarnings("unchecked")
	public JSONObject addReply(ReplyVO r_vo) {
		JSONObject json = new JSONObject();
		String writerImg = "";
		String id = "";
		writerImg = si_dao.insertComment(r_vo);
		if (!"".equals(writerImg)) {
			System.out.println("/////////////// ���� : " + writerImg);
			json.put("result", true);
			json.put("img", writerImg);
			id = r_vo.getId().substring(0, 2) + "***";
			json.put("id", id);
		} // end if
		return json;
	}// addReply

	/**
	 * ���͵� ��� ����Ʈ ���.
	 * 
	 * @param s_num
	 * @return List<StudyCommentDomain>
	 */
	public List<StudyCommentDomain> getStudyComment(String s_num) {
		List<StudyCommentDomain> list = null;
		list = si_dao.selectSCommentList(s_num);

		// ����� �Է��� ������� ���̵� �����.
		String changedId = "";
		for (StudyCommentDomain scd : list) {
			changedId = scd.getId().substring(0, 2) + "***";
			scd.setId(changedId);
		} // end for

		return list;
	}// getStudyComment

	/**
	 * ����� ���� ���.
	 * 
	 * @param s_num
	 * @return
	 */
	public int getScommentCnt(String s_num) {
		int cnt = 0;
		cnt = si_dao.selectScommentCnt(s_num);
		return cnt;
	}// getScommentCnt

	/**
	 * ���͵� ������ ������ �⺻ ���� ���.
	 * 
	 * @param s_num
	 * @return LeaderOfJoinDomain
	 */
	public LeaderOfJoinDomain getLeaderOfJoin(String s_num) {
		LeaderOfJoinDomain loj = null;
		loj = si_dao.selectLeaderOfJoin(s_num);
		return loj;
	}// getLeaderOfJoin

	/**
	 * ������ ��û�� �߰��ϱ�
	 * 
	 * @return
	 */
	public boolean addJoinStudy(JoinFormVO jf_vo) {
		boolean flag = false;

		if (si_dao.insertJoinForm(jf_vo)) {
			flag = true;
		} // end if

		return flag;
	}// addJoinForm

	/******************* Search �������� ����. *******************/

	/**
	 * �� ���� ���͵� ����Ʈ ���.
	 * 
	 * @param my_id
	 * @return List<ThumbnailDomain>
	 */
	public List<ThumbnailDomain> getMyFavStudy(FavStudyOrderVO fso_vo) {
		List<ThumbnailDomain> list = null;

		list = si_dao.selectMyFavStudy(fso_vo);

		String changedNick = "";
		String changedStudyName = "";

		// ������ ���� ���� ����.
		for (ThumbnailDomain td : list) {

			// ������� ���͵� �̸��� 14�� �̻��� ��� "..." ó��.
			if (td.getStudy_name().length() > 14) {
				changedStudyName = td.getStudy_name().substring(0, 14) + "...";
				td.setStudy_name(changedStudyName);
			} // end if

			// ������� nick�� ���̰� 3�� �Ѿ�� "..." ó��.
			if (td.getNick().length() > 3) {
				changedNick = td.getNick().substring(0, 3) + "...";
				td.setNick(changedNick);
			} // end if

		} // end for

		return list;
	}// getMyInterestStudy

	/**
	 * ���ƿ� ���μ���.
	 * 
	 * @param ff_vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject heartProcess(FavFlagVO ff_vo) {
		System.out.println("///////////////////// ����" + ff_vo.getsNum() + " / " + ff_vo.getColor() + " / " + ff_vo.getMy_id());
		JSONObject json = new JSONObject();
		String strFlag = "";
		int cnt = 0;

		// '���ƿ�'���� ���� ������� ��� - �μ�Ʈ �ϱ�.
		if ("gray".equals(ff_vo.getColor())) {
			System.out.println("///////////////////// ����" + ff_vo.getsNum() + " / " + ff_vo.getColor() + " / " + ff_vo.getMy_id());
			// �μ�Ʈ DB �۾� ����.
			cnt = si_dao.insertFavStudy(ff_vo);
			// DB�۾��� ���������� ���� �Ǿ��� ��.
			if (cnt == 1) {
				strFlag = "toI";
				json.put("result", strFlag);
			} // end if
		} // end if

		// ������ �̹� '���ƿ�'�ߴ� ������� ��� - �����.
		if ("red".equals(ff_vo.getColor())) {
			// �μ�Ʈ DB �۾� ����.
			System.out.println("///////////////////// ����" + ff_vo.getsNum() + " / " + ff_vo.getColor() + " / " + ff_vo.getMy_id());

			cnt = si_dao.deleteFavStudy(ff_vo);
			// DB�۾��� ���������� ���� �Ǿ��� ��.
			if (cnt == 1) {
				strFlag = "toR";
				json.put("result", strFlag);
			} // end if
		} // end if

		return json;
	}// end if

	/**
	 * ����� ����Ʈ ���.
	 * 
	 * @return List<ThumbnailDomain>
	 */
	public List<ThumbnailDomain> getThumbnailList(FavSNumFlagVO fsf_vo) {
		List<ThumbnailDomain> list = null;
		list = si_dao.selectThumbnailList();

		String changedNick = "";
		String changedStudyName = "";

		for (ThumbnailDomain td : list) {

			fsf_vo.setMyFavSNum(td.getS_num());

			System.out.println("//////////////////////////////// ���� : " + fsf_vo.getMyFavSNum());

			if (si_dao.selectMyFavSNum(fsf_vo)) {
				td.setFavFlag(true);
			} // end if

			// ������� ���͵� �̸��� 14�� �̻��� ��� "..." ó��.
			if (td.getStudy_name().length() > 14) {
				changedStudyName = td.getStudy_name().substring(0, 14) + "...";
				td.setStudy_name(changedStudyName);
			} // end if

			// ������� nick�� ���̰� 3�� �Ѿ�� "..." ó��.
			if (td.getNick().length() > 3) {
				changedNick = td.getNick().substring(0, 3) + "...";
				td.setNick(changedNick);
			} // end if

		} // end for

		return list;
	}// getThumbnailList

	//////////////////////////////////////// ���͵� ã��

	/**
	 * �� �Խù��� ���� ��� �޼���.
	 * 
	 * @return
	 */
	public int getSearchListCnt(SearchListVO sl_vo) {
		int cnt = 0;
		cnt = si_dao.selectSearchListCnt(sl_vo);
		return cnt;
	}// totalCount()

	/**
	 * �˻��� ����� ����Ʈ ���.
	 * 
	 * @param sl_vo
	 * @return
	 */
	public List<ThumbnailDomain> getSearchList(SearchListVO sl_vo, FavSNumFlagVO fsf_vo) {
		List<ThumbnailDomain> list = null;
		list = si_dao.selectSearchList(sl_vo);

		String changedNick = "";
		String changedStudyName = "";

		// ������ ���� ���� ����.
		for (ThumbnailDomain td : list) {

			fsf_vo.setMyFavSNum(td.getS_num());

			if (si_dao.selectMyFavSNum(fsf_vo)) {
				td.setFavFlag(true);
			} // end if

			// ������� ���͵� �̸��� 14�� �̻��� ��� "..." ó��.
			if (td.getStudy_name().length() > 14) {
				changedStudyName = td.getStudy_name().substring(0, 14) + "...";
				td.setStudy_name(changedStudyName);
			} // end if

			// ������� nick�� ���̰� 3�� �Ѿ�� "..." ó��.
			if (td.getNick().length() > 3) {
				changedNick = td.getNick().substring(0, 3) + "...";
				td.setNick(changedNick);
			} // end if

		} // end for

		return list;
	}// getSearchList

	/**
	 * �� �������� ������ �Խù��� �� ��� �޼���
	 * 
	 * @return
	 */
	public int pageScale() {
		int pageScale = 6;
		return pageScale;
	}// pageScale

	/**
	 * �Խ��� �� ȭ�鿡 ������ �Խù��� �� ��� �޼���
	 * 
	 * @param
	 * @return
	 */
	public int totalPage(int totalCount) {
		// ������ �� �Խù��� �ϳ� �� ������ �������� ���� �Ǿ�� ��.
		// ��ü �Խù����� �������� ������ �Խù��� ���� ������ �ø��� ���ֱ�.
		int totalPage = (int) Math.ceil((totalCount / (double) pageScale()));
		return totalPage;
	}// totalPage

	/**
	 * ���������� ó�� ��ȣ ���ϴ� �޼���.
	 * 
	 * @param currentPage
	 * @return
	 */
	public int startNum(int currentPage) {
		int startNum = 1;
		// ������ �� ���� ��ȣ�� 1, 7, 13 ... ���� �Ǿ�� �Ѵ�.
		// ���۹�ȣ = 6 ( ������������ �Խù��� �� ) - 6 (�� ȭ�鿡 ������ �Խù��Ǽ�) + 1
		startNum = (currentPage * pageScale()) - pageScale() + 1;
		return startNum;
	}// startNum

	/**
	 * ������ �ε��� ����Ʈ���� ��ȸ�� ����ȣ
	 * 
	 * @param startNum
	 * @return
	 */
	public int endNum(int startNum) {
		// �������� �� ��ȣ�� 6, 12, 18 ... ���� �Ǿ�� �Ѵ�.
		// ���� �������� �Խù��� �� ��ȣ ���ϱ�.
		int endNum = startNum + pageScale() - 1;
		return endNum;
	} // endNum

	//////////////////////////////////////// ���͵� ã��

}// class
