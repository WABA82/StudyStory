package kr.co.studystory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.studystory.dao.CommonBbsDAO;
import kr.co.studystory.domain.Alarm;
import kr.co.studystory.domain.NewAlarm;
import kr.co.studystory.vo.AlarmBbsVO;

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
	
}
