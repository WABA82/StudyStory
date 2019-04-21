package kr.co.studystory.dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

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
	
	public SqlSessionFactory getSqlSessionFactory() {
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
	
	/*public static void main(String[] args) {
		NewUserVO nuvo = new NewUserVO("ooo123123", "���ٿ�", "ȣ�ѷѷ�", "12345", "�������", "�������2", "010-2222-3333", "oooooo@ooooo.com", "1", "�뷡��");
		System.out.println(CommonDAO.getInstance().insertSignUp(nuvo));
	}*/
}
