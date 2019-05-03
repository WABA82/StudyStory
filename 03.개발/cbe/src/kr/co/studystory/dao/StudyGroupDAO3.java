package kr.co.studystory.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import kr.co.studystory.domain.AppliedStudy;
import kr.co.studystory.domain.MyStudy;
import kr.co.studystory.domain.StudyIMade;
import kr.co.studystory.vo.ConditionVO;

@Component
public class StudyGroupDAO3 {
	public StudyGroupDAO sg_dao;
	
	public StudyGroupDAO3() {
		sg_dao = StudyGroupDAO.getInstance();
	}
	
	/**
	 * �� ���͵�, ���� ���� ���͵�, ���� ���͵� �����ʶ��� ����� �̹����� �������� �޼���
	 * by ����
	 */
	public String selectMyProfileImg(String id) {
		String img = "";
		
		SqlSession ss = sg_dao.getSqlSessionFactory().openSession();
		img = ss.selectOne("selectMyProfileImg", id);
		
		return img;
	}
	
	/**
	 * �������� ���͵�
	 * by ����
	 */
	public List<StudyIMade> selectStudyImade(ConditionVO cvo) {
		List<StudyIMade> list = null;

		SqlSession ss = sg_dao.getSqlSessionFactory().openSession();
		list = ss.selectList("selectStudyIMade", cvo);
		ss.close();
		
		return list;
	}
	
	/**
	 * �� ���͵� ��ȸ(������)
	 * by ����
	 */
	public List<MyStudy> selectMyStudies(ConditionVO c_vo) {
		List<MyStudy> list=null;
		
		SqlSession ss = sg_dao.getSqlSessionFactory().openSession();
		list=ss.selectList("selectMyStudies",c_vo);
		ss.close();
		
		return list;
	}//selectMyStudies
	
	/**
	 * �� ���͵� ��ȸ(������û��)
	 * by ����
	 */
	public List<AppliedStudy> selectAppliedStudy(ConditionVO c_vo) {
		List<AppliedStudy> list=null;
		
		SqlSession ss = sg_dao.getSqlSessionFactory().openSession();
		list=ss.selectList("selectAppliedStudies",c_vo);
		ss.close();
		
		return list;
	}//selectAppliedStudy
	
	
	
	/*public static void main(String[] args) {
		
		ConditionVO cdvo = new ConditionVO();
		cdvo.setId("young3");
		cdvo.setCategory("���");
		cdvo.setLoc("ȫ��");
		
		StudyGroupDAO2 sgd = new StudyGroupDAO2();
		System.out.println(sgd.selectStudyImade(cdvo));
		
	}*/
	
}//class

















