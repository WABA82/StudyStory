package kr.co.studystory.service;

import java.util.List;
import java.util.concurrent.locks.Condition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.studystory.dao.StudyGroupDAO;
import kr.co.studystory.domain.AppliedStudy;
import kr.co.studystory.domain.MyStudy;
import kr.co.studystory.domain.PrevStudyInfo;
import kr.co.studystory.vo.ConditionVO;
import kr.co.studystory.vo.ModifiedStudyVO;
import kr.co.studystory.vo.NewStudyVO;

@Component
public class StudyGroupService {
	
	@Autowired
	private StudyGroupDAO sg_dao;
	
	//�� ���͵� �����ϱ�
	//���͵�� �ߺ�Ȯ���� ajax�� ����
	public boolean checkDupStudyName(String studyName) {
		boolean flag=false;
		
		if(sg_dao.selectDupStudyName(studyName)) {
			flag=true;
		}
		
		return flag;
	}//checkDupStudyName
	
	public boolean addNewStudy(NewStudyVO ns_vo) {
		boolean flag=false;
		
		if(sg_dao.insertNewStudy(ns_vo)) {
			flag=true;
		}
		return flag;
	}//addNewStudy
	
	//�� ���͵� �����ϱ�
	public PrevStudyInfo getPrevStudy(String sNum) {
		
		StudyGroupDAO sg_dao=StudyGroupDAO.getInstance();
		PrevStudyInfo psi=sg_dao.selectPrevStudyInfo(sNum);
		return psi;
	}//getPrevStudy
	
	public boolean modifyStudy(ModifiedStudyVO ms_vo) {
		boolean flag=false;
		
		StudyGroupDAO sg_dao=StudyGroupDAO.getInstance();
		
		if(sg_dao.updateStudy(ms_vo)) {
			flag=true;
		}
		
		return flag;
	}//modifyStudy
	
	//�� ���͵�
	//������ ������ StudyInfoService�� getMyProfile ���
	public List<MyStudy> getMyStudy(ConditionVO c_vo) {
		List<MyStudy> list=null;
		list=sg_dao.selectMyStudies(c_vo);
		return list;
	}//getMyStudy
	
	public List<AppliedStudy> getMyApplied(ConditionVO c_vo) {
		List<AppliedStudy> list=null;
		list=sg_dao.selectAppliedStudy(c_vo);
		return list;
	}//getMyApplied
	
}































