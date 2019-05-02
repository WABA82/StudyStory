package kr.co.studystory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.studystory.dao.StudyGroupDAO3;
import kr.co.studystory.domain.AppliedStudy;
import kr.co.studystory.domain.MyStudy;
import kr.co.studystory.domain.StudyIMade;
import kr.co.studystory.vo.ConditionVO;

@Component
public class StudyGroupService3 {
	
	@Autowired
	private StudyGroupDAO3 sg_dao3;
	
	/**
	 * �� ���͵�, ���� ���� ���͵�, ���� ���͵𿡼� �����
	 * ������ �̹����� ��ȯ�ϴ� �޼���
	 * by ����
	 */
	public String getMyProfileImg(String id) {
		return sg_dao3.selectMyProfileImg(id);
	}

	/**
	 * ���� ���� ���͵������� ��ȯ�ϴ� �޼���
	 * by ����
	 */
	public List<StudyIMade> getStudyIMade(ConditionVO cvo) {
		return sg_dao3.selectStudyImade(cvo);
	}
	
	/**
	 * �������� ���͵� ������ ��ȯ�ϴ� �޼���
	 * by ����
	 */
	public List<MyStudy> getMyStudy(ConditionVO c_vo) {
		return sg_dao3.selectMyStudies(c_vo);
	}//getMyStudy
	
	/**
	 * ��û�� ���͵� ������ ��ȯ�ϴ� �޼���
	 * by ����
	 */
	public List<AppliedStudy> getMyApplied(ConditionVO c_vo) {
		return sg_dao3.selectAppliedStudy(c_vo);
	}//getMyApplied
}
