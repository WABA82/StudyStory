package kr.co.studystory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.studystory.dao.StudyNoticeDAO;
import kr.co.studystory.domain.StudyNotice;
@Component
public class StudyNoticeService {
	@Autowired
	private StudyNoticeDAO sn_dao;
	
	
	//���͵� �������� ������
	public List<StudyNotice> getSnList(String studyNum){
		List<StudyNotice> list=null;
		list=sn_dao.selectSnList(studyNum);
		
		//StudyNotice sn_do=null;                --���� ���̱� �� ���߿�
		//String 
		
		
		return list;
	}//getSnList

}
