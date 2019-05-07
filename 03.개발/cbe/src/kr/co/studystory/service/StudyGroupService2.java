package kr.co.studystory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.studystory.dao.StudyGroupDAO2;
import kr.co.studystory.domain.JoinBbs;
import kr.co.studystory.domain.Joiner;
import kr.co.studystory.domain.MemberWithImg;
import kr.co.studystory.vo.ApplicantBbsVO;
import kr.co.studystory.vo.DetailJoinerVO;

/**
 * ���͵� ������ ���� - ���� 
 * @author owner
 */
@Component
public class StudyGroupService2 {
	@Autowired
	private StudyGroupDAO2 sg_dao;
	
	public List<MemberWithImg> getMemberWithImg(String sNum){
		List<MemberWithImg> list=null;
		list=sg_dao.selectMemberWithImg(sNum);
		
		return list;
	}//getMemberWithImg
	
	/**
	 * ���͵� ��û�� ���� ����Ʈ - ����
	 * @param abvo
	 * @return
	 */
	public List<JoinBbs> getJoinerList(ApplicantBbsVO abvo){
		List<JoinBbs> list= null;
		list=sg_dao.selectJoinerList(abvo);
		
		return list;
	}//getJoinerList
	
	/**
	 * ��û�� �� �� ��ȸ�ؿ��� �޼��� - ����
	 */
	public int getJoinerTotal(String s_num) {
		return sg_dao.selectJoinerTotal(s_num);
	}
	
	
	/**
	 * 5���� �Խñ��� �� �������� ������
	 * @return
	 */
	public int pageScale() {
		return 5;
	}
	
	/**
	 * ������ ���� ���ϴ� �޼���
	 */
	public int getTotalPage(int totalCnt) {
		int totalPage = totalCnt / pageScale();
		if(totalCnt % pageScale() !=0) {
			totalPage++;
		}
		return totalPage;
	}
	
	/**
	 * �Խñ��� ���۹�ȣ�� ���ϴ� �޼���
	 */
	public int beginNum(int currPage) {
		int begin=1;
		begin=currPage*pageScale()-pageScale()+1;
		return begin;
	}
	
	/**
	 * �Խñ��� ������ ��ȣ�� ���ϴ� �޼���
	 */
	public int endNum(int beginNum) {
		int end = beginNum +pageScale()-1;
		return end;
	}
	
	/**
	 * ������ �ε����� ��ȯ�ϴ� �޼���
	 */
	public int pageIndexNum() {
		return 3;
	}
	
	/**
	 * ���� ������ �ε����� ��ȯ�ϴ� �޼��� 
	 * @return
	 */
	public int startPage(int currPage, int pageIndexNum) {
		int startPage = ((currPage-1)/pageIndexNum) *pageIndexNum+1;
		return startPage;
	}
	
	public int endPage(int startPage, int pageIndexNum, int totalPage) {
		int endPage =(((startPage-1) + pageIndexNum)/ pageIndexNum)*pageIndexNum;
	
	if(totalPage <= endPage) {
		endPage = totalPage;
		}
		return endPage;
	}
	
	/**
	 * ��û�� �󼼺��� 
	 */
	public Joiner getJoiner(DetailJoinerVO djvo) {
		Joiner jr=null;
		jr=sg_dao.selectJoiner(djvo);
		
		return jr;
	}

}//class
