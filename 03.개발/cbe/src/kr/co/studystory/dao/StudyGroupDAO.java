package kr.co.studystory.dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

import kr.co.studystory.domain.MyStudy;
import kr.co.studystory.domain.PrevStudyInfo;
import kr.co.studystory.vo.ModifiedStudyVO;
import kr.co.studystory.vo.NewStudyVO;

@Component
public class StudyGroupDAO {
	private static StudyGroupDAO sg_dao;
	private SqlSessionFactory ssf=null;
	
	public StudyGroupDAO() {

	}
	
	public static StudyGroupDAO getInstance() {
		if(sg_dao==null) {
			sg_dao=new StudyGroupDAO();
		}//end if
		return sg_dao;
	}//getInstance
	
	public synchronized SqlSessionFactory getSqlSessionFactory() {
		if(ssf==null) {
			org.apache.ibatis.logging.LogFactory.useLog4JLogging();
			
			Reader reader=null;
			
			try {
				reader=Resources.getResourceAsReader("kr/co/studystory/dao/mybatis_config.xml");
				SqlSessionFactoryBuilder ssfb=new SqlSessionFactoryBuilder();
				ssf=ssfb.build(reader);
				if(reader!=null) {reader.close();}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//end if
		return ssf;
	}//getSqlSessionFactory

	//내 스터디 개설하기
	public boolean selectDupStudyName(String studyName) {
		boolean flag=false;
		
		StudyGroupDAO sg_dao=StudyGroupDAO.sg_dao;
		SqlSession ss=sg_dao.getSqlSessionFactory().openSession();
		
		
		return flag;
	}//selectDupStudyName
	
	public boolean insertNewStudy(NewStudyVO ns_vo) {
		boolean flag=false;
		SqlSession ss=getSqlSessionFactory().openSession();
		
		int cnt=ss.insert("insertStudy", ns_vo);
		if(cnt==1) {
			flag=true;
			ss.commit();
		}
		ss.close();
		return flag;
	}//insertNewStudy
	
	//내 스터디 수정하기 
	public PrevStudyInfo selectPrevStudyInfo(String sNum) {
		PrevStudyInfo psi=null;
		
		SqlSession ss=getSqlSessionFactory().openSession();
		psi=ss.selectOne("selectPrevStudyInfo", sNum);
		ss.close();
		
		return psi;
	}//selectPrevStudyInfo
	
	public boolean updateStudy(ModifiedStudyVO ms_vo) {
		boolean flag=false;
		
		SqlSession ss=getSqlSessionFactory().openSession();
		
		int cnt=ss.update("updateStudy",ms_vo);
		if(cnt==1) {
			flag=true;
			ss.commit();
		}
		ss.close();
		return flag;
	}
	
	//내 스터디
	public static void main(String[] args) {
		NewStudyVO ns_vo=new NewStudyVO();
		ns_vo.setStudyName("ddd");
		ns_vo.setCategory("취업");
		ns_vo.setContent("ooo");
		ns_vo.setId("ioioio");
		ns_vo.setImg("img");
		ns_vo.setLoc("강남");
		
		System.out.println(sg_dao.insertNewStudy(ns_vo));
	}
}//class

















