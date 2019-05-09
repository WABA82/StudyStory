package kr.co.studystory.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaUtil {
    
    public static String shaEncoding(String plainText) {
          String cipherText = "";
          
          if (plainText != null || !"".equals(plainText)) {
                try {
                      // 1. �˰����� ����� �� �ִ� ��ü�� ��´�.
                      MessageDigest md =  MessageDigest.getInstance("SHA-256");
                      
                      // 2. ��(Plain Text)�� ��ȣ��(Cipher Text)��  ��ȯ
                      md.update(plainText.getBytes());
                      
                      // 3. ��ȯ�� ��ȣ�� (Cipher Text) ���
                      cipherText = new String(md.digest());
                      
                } catch (NoSuchAlgorithmException e) {
                      e.printStackTrace();
                }
          }
          
          return cipherText;
    }
    
    public static void main(String[] args) {
		System.out.println(shaEncoding("tjdwn12Q"));
    	
	}
}