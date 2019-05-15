package kr.co.studystory.admin.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

import kr.co.studystory.admin.domain.DetailNewStudyInfo;
import kr.co.studystory.admin.domain.DetailStudy;
import kr.co.studystory.admin.domain.DetailUser;
import kr.co.studystory.admin.domain.NewStudyInfo;
import kr.co.studystory.admin.domain.StudyInfo;
import kr.co.studystory.admin.domain.UserInfo;
import kr.co.studystory.admin.vo.AcceptVO;
import kr.co.studystory.admin.vo.DetailStudyVO;
import kr.co.studystory.admin.vo.DetailUserVO;
import kr.co.studystory.admin.vo.NsBoardVO;
import kr.co.studystory.admin.vo.StudyBoardVO;
import kr.co.studystory.admin.vo.UserBoardVO;

@Component
public class StudyAndUserDAO {
	private static StudyAndUserDAO sau_dao;
	private SqlSessionFactory ssf=null;
	
	public static StudyAndUserDAO getInstance() {
		if(sau_dao==null) {
			sau_dao=new StudyAndUserDAO();
		}//end if
		return sau_dao;
	}//getInstance
	
	public synchronized SqlSessionFactory getSessionFactory() {
		if(ssf==null) {
			
			Reader reader = null;
			try {
				//1.������ xml �ε�
				reader = Resources.getResourceAsReader("kr/co/studystory/admin/dao/mybatis_config.xml");
				//2. MyBatis Framework����
				SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
				//3. DB�� ������ ��ü �ޱ�
				ssf=ssfb.build(reader);
				if(reader!=null) {
					reader.close();
				}
				
			} catch (IOException ie) {
				ie.printStackTrace();
			}//end catch
		}
		return ssf;
	}//getSessionFactory
	
	/**
	 * NewStudy ����Ʈ ��ȸ
	 * @param nb_vo
	 * @return
	 */
	public List<NewStudyInfo> selectNewStudy(NsBoardVO nb_vo){
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		List<NewStudyInfo> list =ss.selectList("newStudyList",nb_vo);
		ss.close();
		return list;
	}
	
	/**
	 * NewStudy Detail ��ȸ
	 * @param sNum
	 * @return
	 */
	public DetailNewStudyInfo selectDetailNewStudy(String sNum) {
		DetailNewStudyInfo dnsi=null;
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		dnsi= ss.selectOne("nsDetail",sNum);
		ss.close();
		return dnsi;
	}
	
	/**
	 * New Study AcceptFlag ��ȯ
	 * @param a_vo
	 * @return
	 */
	public boolean updeteAccept(AcceptVO a_vo) {
		boolean updateAcceptFlag= false;
		int cnt=0;
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		cnt =ss.update("updateNsAccept",a_vo);
		if(cnt>0) {
			updateAcceptFlag=true;
			ss.commit();
		}
		ss.close();
		return updateAcceptFlag;
	}
	
	/**
	 * NewStudy�� ��� �ֱ�
	 * @param a_vo
	 * @return
	 */
	public boolean insertFirstMember(AcceptVO a_vo) {
		boolean membInsertFlag = false; 
		int cnt=0;
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		cnt= ss.insert("insertNsMember", a_vo);
		if(cnt>0) {
			membInsertFlag=true;
			ss.commit();
		}
		ss.close();
		return membInsertFlag;
	}
	
	/**
	 * NewStudy DeleteFlag ��ȯ
	 * @param sNum
	 * @return
	 */
	public boolean updateDeleteFlag(String sNum) {
		boolean updateDeleteFlag = false; 
		int cnt=0;
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		cnt= ss.update("deleteNewStudy", sNum);
		if(cnt>0) {
			updateDeleteFlag=true;
			ss.commit();
		}
		ss.close();
		return updateDeleteFlag;
	}
	
	/**
	 * ���� ����Ʈ ��ȸ
	 * @param nb_vo
	 * @return
	 */
	public List<UserInfo> selectUserInfo(UserBoardVO ub_vo){
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		List<UserInfo> list =ss.selectList("userInfoList",ub_vo);
		ss.close();
		return list;
	}
	
	/**
	 * ���� ������ ��ȸ
	 * @param id
	 * @return
	 */
	public DetailUser selectDatailUserInfo(String id) {
		DetailUser du=null;
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		du= ss.selectOne("userDetail",id);
		ss.close();
		return du;
	}
	
	/**
	 * User ������ ����
	 * @param du_vo
	 * @return
	 */
	public boolean updateModifyUser(DetailUserVO du_vo) {
		boolean updateModifyUser= false;
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		updateModifyUser =ss.update("updateModUser",du_vo)==1;
		if(updateModifyUser) {
			ss.commit();
		}
		return updateModifyUser;
	}
	
	/**
	 * ���� �� decativation 'Y'�� ����
	 * @param id
	 * @return
	 */
	public boolean updateRemoveUser(String id) {
		boolean updateRemoveUser= false;
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		
		// join, member�� ����� �ִ��� �𸣱� ������ ��ȸ
		int joinNum = ss.selectOne("selectJoinNum", id);
		int memNum = ss.selectOne("selectMemNum",id);
		
		// ��ȸ�� ���ڵ尡 ��� ��������, deactivation flag�� true�� ������Ʈ ó�� �� commit ����
		if (deleteJoinRecord(id, ss, joinNum) && deleteMemberRecord(id, ss, memNum)
				&& ss.update("updateDelUser",id) == 1) {
			ss.commit();
			updateRemoveUser=true;
		}
		ss.close();
		
		return updateRemoveUser;
	}
	
	/**
	 * join���ڵ忡�� ���� ����, join�� �����ϴ� ���� �� �����Ǹ� true
	 * @param id
	 * @return
	 */
	public boolean deleteJoinRecord(String id, SqlSession ss, int joinNum) {
		boolean flag = false;
		
		if (joinNum == ss.delete("delJoinRecord",id)) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * member ���ڵ忡�� ���� ����, member�� �����ϴ� ���� �� �����Ǹ� true
	 * @param id
	 * @return
	 */
	public boolean deleteMemberRecord(String id, SqlSession ss, int memNum) {
		boolean flag = false;
		
		if (memNum == ss.delete("delMemRecord",id)) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * ���� ����Ʈ ��ȸ
	 * @param sb_vo
	 * @return
	 */
	public List<StudyInfo> selectStudyInfo(StudyBoardVO sb_vo){
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		List<StudyInfo> list =ss.selectList("studyInfoList",sb_vo);
		ss.close();
		return list;
	}
	
	/**
	 * �� ���͵� ���� ��ȸ
	 * @param sNum
	 * @return
	 */
	public DetailStudy selectDetailStudyInfo(String sNum) {
		DetailStudy ds=null;
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		ds= ss.selectOne("studyDetail",sNum);
		ss.close();
		return ds;
	}
	
	/**
	 * ���͵� ����
	 * @param ds_vo
	 * @return
	 */
	public boolean updateDetailStudyInfo(DetailStudyVO ds_vo) {
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		boolean updateDetailStudyInfo= ss.update("updateStudy", ds_vo)==1;
		if(updateDetailStudyInfo) {
			ss.commit();
		}
		return updateDetailStudyInfo;
	}
	
	/**
	 * ���� ���� �ִ� �̹��� ������ ���� ��ȸ
	 * @param sNum
	 * @return
	 */
	public String selectPreImg(String sNum) {
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		String preImg= ss.selectOne("selectPreImg",sNum);
		return preImg;
	}
	
	/**
	 * ���͵� ���� �� �˸��� ���� ��� ��ȸ
	 * @param sNum
	 * @return
	 */
	public List<String> selectStudyMember(String sNum) {
		List<String> list= new ArrayList<String>();
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		list= ss.selectList("selectStudyMember",sNum);
		return list;
	}
	
	/**
	 * ���͵� ����
	 * @param sNum
	 * @return
	 */
	public boolean deleteStudy(String sNum) {
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		boolean deleteStudy= ss.update("deleteStudy", sNum)==1;
		if(deleteStudy) {
			ss.commit();
		}
		return deleteStudy;
	}
	
	/**
	 * ȸ�������� �ش� ���̵�� ������ �� ���� ���͵� ����
	 * @param sNum
	 * @return
	 */
	public List<String> selectDeleteSnum(String id) {
		SqlSession ss= StudyAndUserDAO.getInstance().getSessionFactory().openSession();
		List<String> preSnum= ss.selectList("selectDeleteSnum",id);
		return preSnum;
	}
}
