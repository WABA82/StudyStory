package kr.co.studystory.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;

import kr.co.studystory.dao.CommonDAO;
import kr.co.studystory.vo.NewUserVO;

public class CommonService {
	
	private CommonDAO c_dao;
	
	public CommonService() {
		c_dao = CommonDAO.getInstance();
	}
	
	/**
	 * ���̵� �ߺ�Ȯ��
	 * by ���� 190418
	 */
	public JSONObject checkDupId(String id) {
		JSONObject json = new JSONObject();
		json.put("isDup", c_dao.selectDupId(id));
		return json;
	}
	
	/**
	 * �̸��� �ߺ�Ȯ��
	 * by ���� 190418
	 */
	public JSONObject checkDupEmail(String email) {
		JSONObject json = new JSONObject();
		json.put("isDup", c_dao.selectDupEmail(email));
		return json;
	}
	
	/**
	 * ȸ������
	 * by ���� 190418
	 */
	public boolean signUp(NewUserVO nuvo) {
		boolean signedUp = false;
		
		String plainPass = nuvo.getPass();
		nuvo.setPass(shaEncoding(plainPass));
		signedUp = c_dao.insertSignUp(nuvo);
		
		return signedUp;
	}
	
	/**
	 * ��й�ȣ ��ȣȭ ó��
	 * by ���� 190418
	 */
	public static String shaEncoding(String plainText) {
		String cipherText = "";
		Base64 base64 = new Base64();
		
		if (plainText != null || !"".equals(plainText)) {
			try {
				// 1. �˰����� ����� �� �ִ� ��ü�� ��´�.
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				// 2. ��(Plain Text)�� ��ȣ��(Cipher Text)�� ��ȯ 
				md.update(plainText.getBytes());
				// 3. ��ȯ�� ��ȣ�� (Cipher Text) ���
				cipherText = new String(base64.encode(md.digest()));
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return cipherText;
	}

}
