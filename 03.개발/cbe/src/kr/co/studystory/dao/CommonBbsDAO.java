package kr.co.studystory.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

import kr.co.studystory.domain.Alarm;
import kr.co.studystory.domain.DetailAlarm;
import kr.co.studystory.domain.DetailNotice;
import kr.co.studystory.domain.DetailQuestion;
import kr.co.studystory.domain.LoginResult;
import kr.co.studystory.domain.MyQuestion;
import kr.co.studystory.domain.NewAlarm;
import kr.co.studystory.domain.Notice;
import kr.co.studystory.domain.PrevProfile;
import kr.co.studystory.domain.PrevUserInfo;
import kr.co.studystory.vo.AlaramOrQuestionVO;
import kr.co.studystory.vo.AlarmBbsVO;
import kr.co.studystory.vo.ChangePassVO;
import kr.co.studystory.vo.FindIdVO;
import kr.co.studystory.vo.FindPassVO;
import kr.co.studystory.vo.LoginVO;
import kr.co.studystory.vo.ModifiedPassVO;
import kr.co.studystory.vo.ModifiedUserInfoVO;
import kr.co.studystory.vo.NewUserVO;
import kr.co.studystory.vo.NoticeBbsVO;
import kr.co.studystory.vo.OutVO;
import kr.co.studystory.vo.ProfileVO;
import kr.co.studystory.vo.QuestionBbsVO;
import kr.co.studystory.vo.QuestionVO;

@Component
public class CommonBbsDAO {
	
	private static CommonBbsDAO c_dao;
	
	public static CommonBbsDAO getInstance() {
		if (c_dao == null) {
			org.apache.ibatis.logging.LogFactory.useLog4JLogging(); // �α� ���
			c_dao = new CommonBbsDAO();
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
	 * �˸��� ���ǻ��� �Խñ� ���� ��ȯ�ϴ� �޼���
	 * by ���� 190424
	 */
	public int selectTotalCnt(AlaramOrQuestionVO aoqvo) {
		int totalCnt = 0;
		
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		totalCnt = ss.selectOne("selectTotalCnt", aoqvo);
		
		return totalCnt;
	}
	
	/**
	 * �� �˶�(�׺� �ִ�) ������ �� ������ �˶��� ��ȸ�ϴ� �޼���
	 * by ���� 190425
	 */
	public List<NewAlarm> selectNewAlarms(String id) {
		List<NewAlarm> list = null;
		
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		list = ss.selectList("selectNewAlarms", id);
		
		return list;
	}
	
	
	/**
	 * id�� �ش��ϴ� ��ü �˶����� ��ȯ�ϴ� �޼���
	 * by ���� 
	 */
	public int selectAlarmTotal(String id) {
		int alarmTotal = 0;
		
		return alarmTotal;
	}
	
	/**
	 * �˶� ������ �Խ��� �Խñ� ��ȯ�ϴ� �޼���
	 * by ����
	 */
	public List<Alarm> selectAlarms(AlarmBbsVO abv) {
		List<Alarm> list = null;
		
		return list;
	}
	
	/**
	 * �˶� ����ó�� �޼���
	 * by ����
	 */
	public boolean updateReadFlag(String a_num) {
		boolean flag = false;
		
		
		return flag;
	}
	
	/**
	 * �˶� �󼼱��� ��ȸ�ϴ� �޼���
	 * by ����
	 */
	public DetailAlarm selectDetailAlarm(String a_num) {
		DetailAlarm da = null;
		
		
		return da;
	}
	
	/**
	 * ��ü ���������� ��ȯ�ϴ� �޼���
	 * by ����
	 */
	public int selectNoticeTotal() {
		int noticeTotal = 0;

		
		return noticeTotal;
	}
	
	/**
	 * �������� �Խñ��� ��ȸ�ϴ� �޼���
	 * by ����
	 */
	public List<Notice> selectNotice(NoticeBbsVO nbv) {
		List<Notice> list = null;
		
		return list;
	}
	
	/**
	 * �������� ��ȸ���� �ø��� �޼���
	 * by ����
	 */
	public void updateViewCnt(String n_num) {
		
	}
	
	/**
	 * �� ���������� ��ȸ�ϴ� �޼���
	 * by ����
	 */
	public DetailNotice selectDetailNotice(String n_num) {
		DetailNotice dn = null;
		
		return dn;
	}
	
	/**
	 * ���ǻ����� ����ϴ� �޼���
	 * by ����
	 */
	public void insertQuestion(QuestionVO qvo) {
		
	}
	
	/**
	 * ���� ���� ���Ǳ��� ��ȸ�ϴ� �޼���
	 */
	public List<MyQuestion> selectMyQuestion(QuestionBbsVO qbv) {
		List<MyQuestion> list = null;
		
		return list; 
	}
	
	/**
	 * �� ���Ǳ��� ��ȸ�ϴ� �޼���
	 */
	public DetailQuestion selectDetailQuestion(String q_num) {
		DetailQuestion dq = null;
		
		return dq;
	}
	
	
	public static void main(String[] args) {
		System.out.println(CommonBbsDAO.getInstance().selectNewAlarms("kim111"));
	}
	
}
