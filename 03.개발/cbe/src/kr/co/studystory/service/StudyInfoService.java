package kr.co.studystory.service;

import java.util.List;

import kr.co.studystory.dao.StudyInfoDAO;
import kr.co.studystory.domain.LeaderOfJoinDomain;
import kr.co.studystory.domain.StudyCommentDomain;
import kr.co.studystory.domain.StudyInfoDomain;
import kr.co.studystory.domain.ThumbnailDomain;

public class StudyInfoService {

	private StudyInfoDAO si_dao;

	public StudyInfoService() {
		si_dao = StudyInfoDAO.getInstance();
	}// ������.

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

	/************ ���� ���� �޼��� : ���� �׽�Ʈ ************/
	// ���� �׽�Ʈ main.
	public static void main(String[] args) {
//		StudyInfoService sInfoService = new StudyInfoService();
//		LeaderOfJoinDomain inst = sInfoService.getLeaderOfJoin("s_000021");
	}// main

}// class
