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
import kr.co.studystory.vo.JoinAlarmVO;
import kr.co.studystory.vo.JoinDeleteVO;
import kr.co.studystory.vo.NewMemberVO;
import kr.co.studystory.vo.RefuseAlarmVO;
import kr.co.studystory.vo.RefuseVO;

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

	public boolean addNewMember(NewMemberVO nmvo) {
		boolean acceptFlag=false;
		acceptFlag=sg_dao.insertNewMember(nmvo);
		
		String study_name = sg_dao.selectStudyName(nmvo.getS_num());
		
		JoinAlarmVO javo = new JoinAlarmVO("���͵�", "���� �����Ǿ����ϴ�",
				"���� ��û�ϼ̴� ["+study_name+"]�� ������ �����Ǿ����ϴ�. ���� Ȱ�� ��Ź�帳�ϴ�.",
				nmvo.getId());
		sg_dao.insertJoinAlarm(javo);
		
		return acceptFlag;
	}
	
	public boolean removeJoin(JoinDeleteVO jdvo) {
		boolean flag = false;
		System.out.println("=====================22222222222222222");
		flag = sg_dao.deleteJoin(jdvo);
		return flag;
	}
	
	public void sendRefuseAlarm(RefuseVO rfvo) {
		
		String study_name = sg_dao.selectStudyName(rfvo.getS_num());
		RefuseAlarmVO rfavo = new RefuseAlarmVO("���͵�", "������ �����Ǿ����ϴ�",
				"���� ��û�ϼ̴� ["+study_name+"]�� ������ �����Ǿ����ϴ�. ������ ������ �����ϴ�.\n���� ���� : "
				+rfvo.getReason(), rfvo.getId());
		
		sg_dao.insertRefuseAlarm(rfavo);
	}
	
	
}//class
