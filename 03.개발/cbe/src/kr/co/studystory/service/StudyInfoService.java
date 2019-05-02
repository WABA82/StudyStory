package kr.co.studystory.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import kr.co.studystory.dao.StudyInfoDAO;
import kr.co.studystory.domain.LeaderOfJoinDomain;
import kr.co.studystory.domain.StudyCommentDomain;
import kr.co.studystory.domain.StudyInfoDomain;
import kr.co.studystory.domain.ThumbnailDomain;
import kr.co.studystory.vo.JoinAlarmVO;
import kr.co.studystory.vo.JoinFormVO;
import kr.co.studystory.vo.ReplyVO;
import kr.co.studystory.vo.SearchSelectVO;

public class StudyInfoService {

	private StudyInfoDAO si_dao;

	public StudyInfoService() {
		si_dao = StudyInfoDAO.getInstance();
	}// ������.

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
	 * ���͵� �� �������� ����� �Է��ϱ�.
	 * 
	 * @param reply
	 */
	@SuppressWarnings("unchecked")
	public JSONObject addReply(ReplyVO r_vo) {
		JSONObject json = new JSONObject();
		int cnt = si_dao.insertComment(r_vo);
		json.put("result", cnt == 1);
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
	public String addJoinForm(JoinFormVO jf_vo, JoinAlarmVO ja_vo) {
		String msg = "";
		if (si_dao.insertJoinForm(jf_vo, ja_vo) == 2) {
			msg = "���� ��û�� ���������� �̷�� �����ϴ�.";
		} // end if
		return msg;
	}// addJoinForm

	/**
	 * �� ���� ���͵� ����Ʈ ���.
	 * 
	 * @param my_id
	 * @return List<ThumbnailDomain>
	 */
	public List<ThumbnailDomain> getMyInterestStudy(String my_id) {
		List<ThumbnailDomain> list = null;
		list = si_dao.selectMyFavStudy(my_id);
		// ������� nick�� ���̰� 3�� �Ѿ�� "..." ó��.
		String changedNick = "";
		for (ThumbnailDomain td : list) {
			if (td.getNick().length() > 3) {
				changedNick = td.getNick().substring(0, 3) + "...";
				td.setNick(changedNick);
			} // end if
		} // end for
		return list;
	}// getMyInterestStudy

	/******************* Search �������� ����. *******************/

	/**
	 * ����� ����Ʈ ���.
	 * 
	 * @return List<ThumbnailDomain>
	 */
	public List<ThumbnailDomain> getThumbnailList() {
		List<ThumbnailDomain> list = null;
		list = si_dao.selectThumbnailList();
		// ������� nick�� ���̰� 3�� �Ѿ�� "..." ó��.
		ThumbnailDomain thumb_domain = null;
		String nick = "";
		for (int i = 0; i < list.size(); i++) {
			thumb_domain = list.get(i);
			nick = thumb_domain.getNick();
			if (nick.length() > 3) {
				nick = nick.substring(0, 3) + "...";
				thumb_domain.setNick(nick);
			} // end if
		} // end for
		return list;
	}// getThumbnailList

	/**
	 * �� ���� ������ ���� ���Ӱ� ���ĵ� ������� ��� �޼���.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getOrderedList(String order) {

		JSONObject oneJsonObj = null; // �����Domain�� �� ���� ������ JSON��ü.
		JSONArray jsonArr = null; // JSON��ü�� ���� JSON�迭.
		JSONObject typeJson = null; // JSON�迭�� �����ϰ� ������ JSON��ü.

		// �ֽż����� ����.
		if ("�ֽż�".equals(order)) {

			// DB���� �ֽż����� ��ȸ�ϱ�.
			List<ThumbnailDomain> list = si_dao.selectThumbLatest();

			// JSONObject�� ���� ����� ����.
			ThumbnailDomain thumb = null;

			// JSONObject�� ���� JSONArray
			jsonArr = new JSONArray();

			// list�� ���� JSONObject�� ��ȯ�� JSONArray�� ���.
			for (int i = 0; i < list.size(); i++) {
				oneJsonObj = new JSONObject();

				thumb = list.get(i);

				oneJsonObj.put("sNum", thumb.getsNum());
				oneJsonObj.put("studyName", thumb.getStudyName());
				oneJsonObj.put("loc", thumb.getLoc());
				oneJsonObj.put("category", thumb.getCategory());
				oneJsonObj.put("img", thumb.getImg());
				oneJsonObj.put("recruitment", thumb.getRecruitment());
				oneJsonObj.put("inputDate", thumb.getInputDate());
				oneJsonObj.put("nick", thumb.getNick());
				oneJsonObj.put("userImg", thumb.getUserImg());
				oneJsonObj.put("favFlag", thumb.isFavFlag());

				jsonArr.add(oneJsonObj);
			} // end for

			// ������ JSON��ü ����.
			typeJson = new JSONObject();

			// JSON�迭 ���.
			typeJson.put("latest_order", jsonArr);

			// ���� �׽�Ʈ.
			System.out.println("/////////////////////// ���� json�迭�� ũ�� : " + jsonArr.size());

		} // end if

		return typeJson;
	}// getLatestThumbList

	/**
	 * �˻� ���� VO�� �޾� �ش� ����� List�� ��� �޼���.
	 * 
	 * @param ss_vo
	 * @return
	 */
	public List<ThumbnailDomain> getConditionalThumbList(SearchSelectVO ss_vo) {
		List<ThumbnailDomain> list = null;

		list = si_dao.selectConditionalThumbList(ss_vo);
		
		return list;
	}// getConditionalThumbList

}// class
