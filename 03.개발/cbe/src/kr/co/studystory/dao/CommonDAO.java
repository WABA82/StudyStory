package kr.co.studystory.dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.co.studystory.domain.LoginResult;
import kr.co.studystory.domain.PrevUserInfo;
import kr.co.studystory.vo.ChangePassVO;
import kr.co.studystory.vo.FindIdVO;
import kr.co.studystory.vo.FindPassVO;
import kr.co.studystory.vo.LoginVO;
import kr.co.studystory.vo.ModifiedUserInfoVO;
import kr.co.studystory.vo.NewUserVO;

public class CommonDAO {
	
	private static CommonDAO c_dao;
	
	public static CommonDAO getInstance() {
		if (c_dao == null) {
			org.apache.ibatis.logging.LogFactory.useLog4JLogging(); // �α� ���
			c_dao = new CommonDAO();
		}
		
		return c_dao;
	}
	
	public synchronized SqlSessionFactory getSqlSessionFactory() {
		SqlSessionFactory ssf = null;
		
		try {
			Reader r = Resources.getResourceAsReader("kr/co/studystory/dao/mybatis_config.xml");
			SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
			ssf = ssfb.build(r);
			if (r != null) {
				r.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ssf;
	}
	
	/**
	 * �α���
	 * by ���� 190422
	 */
	public LoginResult selectLogin(LoginVO lvo) {
		LoginResult lr = new LoginResult();
		
		System.out.println("======DAO lvo :"+ lvo.getId()+"/"+lvo.getPass());
		
		SqlSession ss = CommonDAO.getInstance().getSqlSessionFactory().openSession();
		int cnt = ss.selectOne("login",lvo);
		ss.close();
		System.out.println("=============== cnt :: " + cnt);
		if (cnt == 1) {
			lr.setLogged(true);
			lr.setMsg(null);
		} else {
			lr.setLogged(false);
			lr.setMsg("�α��ο� �����߽��ϴ�");
		}
		
		return lr;
	}
	
	/**
	 * Ż���������� ��ȸ
	 * false�� Ż������
	 * true�� Ȱ������ ����
	 * by ���� 190422
	 */
	public boolean selectDeactivation(String id) {
		boolean flag = false; 
		
		SqlSession ss = CommonDAO.getInstance().getSqlSessionFactory().openSession();
		String selectResult = ss.selectOne("checkDeactivation",id);
		ss.close();
		if (selectResult == null) { // null�̸� Ȱ������ ���� = true
			flag = true;
		}
		
		System.out.println("========= deactive flag : "+flag);
		return flag;
	}
	
	
	/**
	 * ���̵� �ߺ�üũ
	 * by ���� 190418
	 */
	public boolean selectDupId(String id) {
		boolean flag = false;
		
		SqlSession ss = CommonDAO.getInstance().getSqlSessionFactory().openSession();
		int cnt = ss.selectOne("checkDupId",id);
		ss.close();
		
		if (cnt == 1) { // ���̵� �����ϸ� true
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * �̸��� �ߺ�üũ
	 * by ���� 190418
	 */
	public boolean selectDupEmail(String email) {
		boolean flag = false;
		
		SqlSession ss = CommonDAO.getInstance().getSqlSessionFactory().openSession();
		int cnt = ss.selectOne("checkDupEmail",email);
		ss.close();
		
		if (cnt == 1) { // �̸����� �����ϸ� true
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * ȸ������
	 * by ���� 190418
	 */
	public boolean insertSignUp(NewUserVO nuvo) {
		boolean flag = false;
		
		SqlSession ss = CommonDAO.getInstance().getSqlSessionFactory().openSession();
		int cnt = ss.insert("insertNewUser",nuvo);
		
		if (cnt == 1) { // ȸ�������� ����Ǹ� true
			flag = true; 
			ss.commit();
		}
		ss.close();
		
		return flag;
	}
	
	/**
	 * ȸ�� ���� �� nick���
	 * by ���� 190421
	 */
	public String selectNick(String id) {
		String nick = "";
		
		SqlSession ss = CommonDAO.getInstance().getSqlSessionFactory().openSession();
		nick = ss.selectOne("selectNick", id);
		ss.close();
		
		return nick;
	}
	
	/**
	 * ���̵� ã��
	 * by ���� 190421
	 */
	public String selectId(FindIdVO fivo) {
		String id = "";
		
		SqlSession ss = CommonDAO.getInstance().getSqlSessionFactory().openSession();
		id = ss.selectOne("selectId", fivo);
		ss.close();
		
		return id;
	}
	
	public boolean selectAnswer(FindPassVO fpvo) {
		boolean flag = false;
		
		SqlSession ss = CommonDAO.getInstance().getSqlSessionFactory().openSession();
		int cnt = ss.selectOne("selectPass", fpvo);
		ss.close();
		if (cnt == 1) {
			flag = true;
		}
		
		return flag; 
	}
	
	public boolean updatePass(ChangePassVO cpvo) {
		boolean flag = false;
		
		SqlSession ss = CommonDAO.getInstance().getSqlSessionFactory().openSession();
		int cnt = ss.update("updatePass", cpvo);
		if (cnt == 1) {
			ss.commit();
			flag = true;
		}
		ss.close();
		
		return flag;
	}
	
	public PrevUserInfo selectPrevUserInfo(String id) {
		PrevUserInfo pui = null;
		
		SqlSession ss = CommonDAO.getInstance().getSqlSessionFactory().openSession();
		pui = ss.selectOne("selectPrevUserInfo", id);
		ss.close();
		
		return pui;
	}
	
	public boolean updateUserInfo(ModifiedUserInfoVO muivo) {
		boolean flag = false;
		
		SqlSession ss = CommonDAO.getInstance().getSqlSessionFactory().openSession();
		System.out.println("============cnt!!!! : ");
		int cnt = ss.update("updateUserInfo",muivo);
		System.out.println("============cnt!!!! : "+cnt);
		
		if (cnt == 1) {
			flag = true;
			ss.commit();
		}
		ss.close();

		return flag;
	}
	
	public static void main(String[] args) {
		
		ModifiedUserInfoVO muivo = new ModifiedUserInfoVO();
		muivo.setId("young");
		muivo.setAddr1("�־ȹٲ��������");
		muivo.setAddr2("��ü ��!!!");
		muivo.setEmail("ooosss@kkkcff.com");
		muivo.setTel("010-2312-1234");
		muivo.setName("���ƾ�");
		muivo.setZipcode("00000");
		muivo.setPass("Bjb7hU0s3KxFxH1+dy6tiWs9XlM=");
		System.out.println(CommonDAO.getInstance().updateUserInfo(muivo));
	}
}
