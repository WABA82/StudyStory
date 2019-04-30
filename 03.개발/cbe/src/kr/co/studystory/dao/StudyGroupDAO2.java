package kr.co.studystory.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

import kr.co.studystory.domain.MemberWithImg;
@Component
public class StudyGroupDAO2 {

	private static StudyGroupDAO2 sg_dao;
	private SqlSessionFactory ssf=null;
	
	private StudyGroupDAO2() {
	}//StudyNoticeDAO
	
	public static StudyGroupDAO2 getInstance() {
		if(sg_dao==null) {
			sg_dao=new StudyGroupDAO2();
		}//end if
		return sg_dao;
	}//getInstance
	
	public synchronized SqlSessionFactory getSessionFactory() {
		if(ssf==null) {
		Reader reader=null;

		try {
			reader=Resources.getResourceAsReader("kr/co/studystory/dao/mybatis_config.xml");
			SqlSessionFactoryBuilder ssfb= new SqlSessionFactoryBuilder();
			ssf=ssfb.build(reader);
			if(reader !=null) {reader.close();}
		} catch (IOException ie) {
			ie.printStackTrace();
		}//end catch
		}//end if
		return ssf;
	}//getSessionFactory
		
	
	//���͵� ������ ���� - ����
	public int selectAllMember(String sNum) {
		int cnt=0;
		
		
		return cnt;
	}//selectAllMember
	
	/**
	 * ���͵� ������ ���� ( �̹������� ) - ����
	 * @param sNum
	 * @return
	 */
	public List<MemberWithImg> selectMemberWithImg(String sNum){
		List<MemberWithImg> list=null;
		
		
		return list;
	}//selectMemberWithImg
	
}
