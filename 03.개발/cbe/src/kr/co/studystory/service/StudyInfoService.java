package kr.co.studystory.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.studystory.dao.StudyInfoDAO;
import kr.co.studystory.domain.LeaderOfJoinDomain;
import kr.co.studystory.domain.StudyCommentDomain;
import kr.co.studystory.domain.StudyInfoDomain;
import kr.co.studystory.domain.ThumbnailDomain;
import kr.co.studystory.domain.WriterInfoDomain;
import kr.co.studystory.vo.DetailMenuVO;
import kr.co.studystory.vo.FavFlagVO;
import kr.co.studystory.vo.FavSNumFlagVO;
import kr.co.studystory.vo.FavStudyOrderVO;
import kr.co.studystory.vo.JoinFormVO;
import kr.co.studystory.vo.MainFavListVO;
import kr.co.studystory.vo.MainLatestListVO;
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
		WriterInfoDomain wid = si_dao.insertComment(r_vo);
		if (!"".equals(wid.getNick())) {
			json.put("result", true);
			json.put("img", wid.getImg());
			json.put("nick", wid.getNick());
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

	//////////////////////////////////////// ����

	/**
	 * ����� ����Ʈ ���.
	 * 
	 * @return List<ThumbnailDomain>
	 */
	public List<ThumbnailDomain> getFavThList(MainFavListVO mfl_vo) {
		List<ThumbnailDomain> list = null;
		list = si_dao.selectFavThList(mfl_vo);

		String changedNick = "";
		String changedStudyName = "";

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

		System.out.println("////////////////////////// ���� : " + list);

		return list;
	}// getThumbnailList

	/**
	 * ����� ����Ʈ ���.
	 * 
	 * @return List<ThumbnailDomain>
	 */
	public List<ThumbnailDomain> getLatestThList(MainLatestListVO mll_vo) {
		List<ThumbnailDomain> list = null;
		list = si_dao.selectLatestThList(mll_vo);

		String changedNick = "";
		String changedStudyName = "";

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
	}// getThumbnailList

	/**
	 * ����� ����Ʈ JSON���� ��ȯ�ϱ�
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getMainFavListProcess(MainFavListVO mfl_vo) {
		JSONObject json = new JSONObject();
		JSONObject data = null;
		JSONArray jsonArr = new JSONArray();

		List<ThumbnailDomain> list = si_dao.selectFavThList(mfl_vo);

		String changedNick = "";
		String changedStudyName = "";

		ThumbnailDomain td = null;

		for (int i = 0; i < list.size(); i++) {

			data = new JSONObject();

			td = list.get(i);
			
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

			data.put("s_num", td.getS_num());
			data.put("study_name", td.getStudy_name());
			data.put("loc", td.getLoc());
			data.put("category", td.getCategory());
			data.put("img", td.getImg());
			data.put("recruitment", td.getRecruitment());
			data.put("input_date", td.getInput_date());
			data.put("nick", td.getNick());
			data.put("user_img", td.getUser_img());

			jsonArr.add(i, data);
		} // end for
		
		json.put("jsonArr", jsonArr);
		json.put("resultFlag", "fav");

		return json;
	}// getMainFavListProcess

	
	/**
	 * ����� ����Ʈ JSON���� ��ȯ�ϱ�
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getMainLatestListProcess(MainLatestListVO mll_vo) {
		JSONObject json = new JSONObject();
		JSONObject data = null;
		JSONArray jsonArr = new JSONArray();
		
		List<ThumbnailDomain> list = si_dao.selectLatestThList(mll_vo);
		
		String changedNick = "";
		String changedStudyName = "";
		
		ThumbnailDomain td = null;
		
		for (int i = 0; i < list.size(); i++) {
			
			data = new JSONObject();
			
			td = list.get(i);
			
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
			
			data.put("s_num", td.getS_num());
			data.put("study_name", td.getStudy_name());
			data.put("loc", td.getLoc());
			data.put("category", td.getCategory());
			data.put("img", td.getImg());
			data.put("recruitment", td.getRecruitment());
			data.put("input_date", td.getInput_date());
			data.put("nick", td.getNick());
			data.put("user_img", td.getUser_img());
			
			jsonArr.add(i, data);
		} // end for
		
		json.put("jsonArr", jsonArr);
		json.put("resultFlag", "latest");
		
		return json;
	}// getMainFavListProcess

	/**
	 * �� �������� ������ �Խù��� �� ��� �޼���
	 * 
	 * @return
	 */
	public int mainPageScale() {
		int pageScale = 4;
		return pageScale;
	}// pageScale

	/**
	 * �� ������ �� ��� �޼���
	 * 
	 * @param
	 * @return
	 */
	public int mainTotalPage(int totalCount) {
		// ������ �� �Խù��� �ϳ� �� ������ �������� ���� �Ǿ�� ��.
		// ��ü �Խù����� �������� ������ �Խù��� ���� ������ �ø��� ���ֱ�.
		int totalPage = (int) Math.ceil((totalCount / (double) mainPageScale()));
		return totalPage;
	}// totalPage

	/**
	 * ���������� ó�� ��ȣ ���ϴ� �޼���.
	 * 
	 * @param currentPage
	 * @return
	 */
	public int mainStartNum(int currentPage) {
		int startNum = 1;
		// ������ �� ���� ��ȣ�� 1, 7, 13 ... ���� �Ǿ�� �Ѵ�.
		// ���۹�ȣ = 6 ( ������������ �Խù��� �� ) - 6 (�� ȭ�鿡 ������ �Խù��Ǽ�) + 1
		startNum = (currentPage * mainPageScale()) - mainPageScale() + 1;
		return startNum;
	}// startNum

	/**
	 * ������ �ε��� ����Ʈ���� ��ȸ�� ����ȣ
	 * 
	 * @param startNum
	 * @return
	 */
	public int mainEndNum(int startNum) {
		// �������� �� ��ȣ�� 6, 12, 18 ... ���� �Ǿ�� �Ѵ�.
		// ���� �������� �Խù��� �� ��ȣ ���ϱ�.
		int endNum = startNum + mainPageScale() - 1;
		return endNum;
	} // endNum

	//////////////////////////////////////// ����

	//////////////////////////////////////// ���� ���͵�

	/**
	 * ���� ���͵� ������� �� ���� ���.
	 * 
	 * @param ff_vo
	 * @return
	 */
	public int getFavStudyCnt(FavStudyOrderVO fso_vo) {
		int cnt = 0;
		cnt = si_dao.selectFavStudyCnt(fso_vo);
		return cnt;
	}// totalCount()

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
		System.out.println(
				"///////////////////// ����" + ff_vo.getsNum() + " / " + ff_vo.getColor() + " / " + ff_vo.getMy_id());
		JSONObject json = new JSONObject();
		String strFlag = "";
		int cnt = 0;

		// '���ƿ�'���� ���� ������� ��� - �μ�Ʈ �ϱ�.
		if ("gray".equals(ff_vo.getColor())) {
			System.out.println("///////////////////// ����" + ff_vo.getsNum() + " / " + ff_vo.getColor() + " / "
					+ ff_vo.getMy_id());
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
			System.out.println("///////////////////// ����" + ff_vo.getsNum() + " / " + ff_vo.getColor() + " / "
					+ ff_vo.getMy_id());

			cnt = si_dao.deleteFavStudy(ff_vo);
			// DB�۾��� ���������� ���� �Ǿ��� ��.
			if (cnt == 1) {
				strFlag = "toR";
				json.put("result", strFlag);
			} // end if
		} // end if

		return json;
	}// end if

	//////////////////////////////////////// ���� ���͵�

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
	 * �� ������ �� ��� �޼���
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

	// �ε��� �����ϱ�.
	public int pageIndexNum() {
		return 3;
	}// pageIndexNum

	public int startPage(int currPage, int pageIndexNum) {
		int startPage = ((currPage - 1) / pageIndexNum) * pageIndexNum + 1;
		return startPage;
	}// startPage

	public int endPage(int startPage, int pageIndexNum, int totalPage) {
		int endPage = (((startPage - 1) + pageIndexNum) / pageIndexNum) * pageIndexNum;
		if (totalPage <= endPage) {
			endPage = totalPage;
		} // end if
		return endPage;
	}// endPage

	//////////////////////////////////////// ���͵� ã��

}// class
