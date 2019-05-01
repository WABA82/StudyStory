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
	 * ���� ���� ���͵������� ��ȯ�ϴ� �޼���
	 * by ����
	 */
	public List<StudyIMade> getStudyIMade(ConditionVO cvo) {
		return sg_dao3.selectStudyImade(cvo);
	}
	
	//�� ���͵�
	//������ ������ StudyInfoService�� getMyProfile ���
	public List<MyStudy> getMyStudy(ConditionVO c_vo) {
		List<MyStudy> list=null;
		list=sg_dao3.selectMyStudies(c_vo);
		return list;
	}//getMyStudy
	
	public List<AppliedStudy> getMyApplied(ConditionVO c_vo) {
		List<AppliedStudy> list=null;
		list=sg_dao.selectAppliedStudy(c_vo);
		return list;
	}//getMyApplied
}
