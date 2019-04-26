package kr.co.studystory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.studystory.dao.CommonBbsDAO;
import kr.co.studystory.domain.Alarm;
import kr.co.studystory.domain.DetailAlarm;
import kr.co.studystory.domain.DetailNotice;
import kr.co.studystory.domain.NewAlarm;
import kr.co.studystory.domain.Notice;
import kr.co.studystory.vo.AlarmBbsVO;
import kr.co.studystory.vo.NoticeBbsVO;

@Component
public class CommonBbsService {

	@Autowired
	private CommonBbsDAO cb_dao;
	
	/**
	 * 10���� �Խñ��� �� �������� ������ ��
	 */
	public int pageScale() {
		return 10;
	}
	
	/**
	 * ������ ���� ���ϴ� �޼���
	 */
	public int getTotalPage(int totalCnt) {
		int totalPage = totalCnt / pageScale();
		if (totalCnt % pageScale() != 0) {
			totalPage++;
		}
		return totalPage;
	}
	
	/**
	 * �Խñ��� ���۹�ȣ�� ���ϴ� �޼���
	 */
	public int beginNum(int currPage) {
		int begin = 1;
		begin = currPage * pageScale() - pageScale() + 1;
		return begin;
	}
	
	/**
	 * �Խñ��� ������ ��ȣ�� ���ϴ� �޼���
	 */
	public int endNum(int beginNum) {
		int end = beginNum + pageScale() - 1;
		return end;
	}
	
	/**
	 * ������ �ε����� ��ȯ�ϴ� �޼��� 
	 */
	public int pageIndexNum() {
		return 5;
	}
	
	/**
	 * ���� ������ �ε����� ��ȯ�ϴ� �޼���
	 */
	public int startPage(int currPage, int pageIndexNum) {
		int startPage = ((currPage - 1)/pageIndexNum) * pageIndexNum + 1;
		return startPage;
	}
	
	/**
	 * ������ ������ �ε����� ��ȯ�ϴ� �޼���
	 */
	public int endPage(int startPage, int pageIndexNum, int totalPage) {
		int endPage = (((startPage - 1) + pageIndexNum) / pageIndexNum)*pageIndexNum;
		
		if (totalPage <= endPage) {
			endPage = totalPage;
		}
		
		return endPage;
	}
	
	/**
	 * �� �˶� ��ȸ �� ��ȯ
	 * by ���� 190424
	 */
	public List<NewAlarm> getNewAlarms(String id) {
		return cb_dao.selectNewAlarms(id);
	}
	
	/**
	 * �ش� ������ ��ü �˶��� ��ȯ
	 * by ���� 190425
	 */
	public int getAlarmTotal(String id) {
		return cb_dao.selectAlarmTotal(id);
	}
	
	/**
	 * �˶� ����Ʈ ��ȸ �� ��ȯ
	 * by ���� 190425
	 */
	public List<Alarm> getAlarms(AlarmBbsVO abv) {
		return cb_dao.selectAlarms(abv);
	}
	
	/**
	 * �˶� ����ȸ + ����ó��
	 * by ���� 190426
	 */
	public DetailAlarm getDetailAlarm(String a_num) {
		cb_dao.updateReadFlag(a_num);
		return cb_dao.selectDetailAlarm(a_num);
	}
	
	/**
	 * ���� ��ü ���� ��ȸ�ϴ� �޼���
	 * by ���� 190426
	 */
	public int getNoticeTotal(String serachWord) {
		return cb_dao.selectNoticeTotal(serachWord);
	}
	
	/**
	 * ���� �Խñ��� ��ȸ�ϴ� �޼���
	 * by ���� 190426
	 */
	public List<Notice> getNotices(NoticeBbsVO nbvo) {
		return cb_dao.selectNotice(nbvo); 
	}
	
	/**
	 * �� �Խñ� ������ ��ȸ�ϴ� �޼��� + ��ī��Ʈ++
	 * by ���� 190426
	 */
	public DetailNotice getDetailNotice(String n_num) {
		cb_dao.updateViewCnt(n_num);
		return cb_dao.selectDetailNotice(n_num);
	}
	
	
}
