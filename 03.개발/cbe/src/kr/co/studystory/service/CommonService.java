package kr.co.studystory.service;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.co.studystory.dao.CommonDAO;
import kr.co.studystory.domain.LoginResult;
import kr.co.studystory.domain.PrevProfile;
import kr.co.studystory.domain.PrevUserInfo;
import kr.co.studystory.vo.ChangePassVO;
import kr.co.studystory.vo.FindIdVO;
import kr.co.studystory.vo.FindPassVO;
import kr.co.studystory.vo.LoginVO;
import kr.co.studystory.vo.ModifiedPassVO;
import kr.co.studystory.vo.ModifiedUserInfoVO;
import kr.co.studystory.vo.NewUserVO;
import kr.co.studystory.vo.OutVO;
import kr.co.studystory.vo.ProfileImgVO;
import kr.co.studystory.vo.ProfileVO;

@Component
public class CommonService {
	
	@Autowired
	private CommonDAO c_dao;
	
	public LoginResult login(LoginVO lvo) {
		LoginResult lr = c_dao.selectLogin(lvo);
		if(!c_dao.selectDeactivation(lvo.getId())) {
			lr.setLogged(false);
			lr.setMsg("Ż���� �����Դϴ�");
		}
		return lr;
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
	
	/**
	 * ȸ������ �� nick���
	 * by ���� 190421
	 */
	public String getNick(String id) {
		String nick = "";
		nick = c_dao.selectNick(id);
		return nick;
	}
	
	/**
	 * ���̵� ã��
	 * by ���� 190421
	 */
	public String findId(FindIdVO fivo) {
		String id = "";
		id = c_dao.selectId(fivo);
		return id;
	}
	
	/**
	 * ��й�ȣ ã��(����)
	 * by ���� 190421
	 */
	public boolean findPass(FindPassVO fpvo) {
		boolean flag = false;
		flag = c_dao.selectAnswer(fpvo);
		return flag;
	}
	
	/**
	 * ���ο� ��й�ȣ ����
	 * by ���� 190421
	 */
	public boolean setNewPass(ChangePassVO cpvo) {
		boolean flag = false;
		flag = c_dao.updatePass(cpvo);
		return flag;
	}
	
	/**
	 * ������ ���� ��
	 * ���� ������ ��������
	 * by ���� 190421
	 */
	public PrevProfile getProfile(String id) {
		PrevProfile pv = null;
		pv = c_dao.selectProfile(id);
		return pv;
	}
	
	/**
	 * ���� �г��� �ߺ�üũ,
	 * true�� �ߺ�
	 * by ���� 190423
	 */
	public boolean checkDupNick(String nick) {
		boolean flag = false;
		
		if (c_dao.selectDupNick(nick)) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * ���ο� �̹��� ���ε� �޼���, ���ε�� ���ϸ� ��ȯ
	 * by ���� 190423
	 */
	public String uploadNewImg(HttpServletRequest request) {
		
		int maxSize = 1024*1024*10;
		String name = "";
		
		File uploadDir = new File("C:/release_0515/profile_img");
		
		// 1. ���� - ���� ���ε�
		MultipartRequest mr = null;
		try {
			mr = new MultipartRequest(request, uploadDir.getAbsolutePath(), 
					maxSize, "UTF-8", new DefaultFileRenamePolicy());
			
			// param���� ���� img(���� ����)�� ����
			String prevImg = mr.getParameter("prevImg");
			
			if (!"no_user_img.png".equals(prevImg)) {
				File file = new File("C:/release_0515/profile_img/"
						+prevImg);
				
				if (file.exists()) { // ������ �����ϸ� ����
					file.delete();
				}
			}
			
			// 2. �Ķ���� ó��
			name = mr.getFilesystemName("upFile");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return name;
	}
	
	/**
	 * �������̹��� ����
	 * by ���� 190423
	 */
	public boolean setProfileImg(ProfileImgVO pivo) {
		boolean flag = false;
		
		if (c_dao.updateProfileImg(pivo)) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * ���ο� �Է�����(�г���, �ڱ�Ұ�)�� ���������� ����
	 * by ���� 190423
	 */
	public boolean setProfile(ProfileVO pv) {
		boolean flag = false;
		
		if (c_dao.updateProfile(pv)) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * ȸ������ ���� �� ���� ȸ������ ��ȸ
	 * by ���� 190422 
	 */
	public PrevUserInfo selectMyInfo(String id) {
		PrevUserInfo pui = null;
		pui = c_dao.selectPrevUserInfo(id);
		return pui;
	}
	
	/**
	 * ȸ������ ����
	 * by ���� 190422
	 */
	public boolean changeUserInfo(ModifiedUserInfoVO muivo) {
		boolean flag = false;
		if(c_dao.updateUserInfo(muivo)) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * ��й�ȣ ����
	 * by ���� 190422
	 */
	public boolean changePass(ModifiedPassVO mpvo) {
		boolean flag = false;
		if (c_dao.updateNewPass(mpvo)) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * ȸ�� Ż�� ó��
	 * by ���� 190422
	 */
	public boolean setDeactivation(OutVO ovo) {
		boolean flag = false;
		
		if (c_dao.updateDeactivation(ovo)) {
			c_dao.deleteStudyMember(ovo.getId());
			flag = true;
		}
		
		return flag;
	}
}
