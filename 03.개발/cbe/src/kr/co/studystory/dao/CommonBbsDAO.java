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
import kr.co.studystory.domain.MyQuestion;
import kr.co.studystory.domain.NewAlarm;
import kr.co.studystory.domain.Notice;
import kr.co.studystory.vo.AlarmBbsVO;
import kr.co.studystory.vo.NoticeBbsVO;
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
	 * �׺�� �̹����� �ٲٱ� ���� ���ο� �˶��� �ִ��� Ȯ���ϴ� �޼���
	 * by ���� 0507
	 */
	public boolean selectNewAlarmFlag(String id) {
		boolean flag = false;
		
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		int cnt = ss.selectOne("selectNewAlarmFlag", id);
		flag = cnt != 0;
		ss.close();
		
		return flag;
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
	 * by ���� 190426
	 */
	public int selectAlarmTotal(String id) {
		int alarmTotal = 0;
		
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		alarmTotal = ss.selectOne("selectAlarmTotal", id);
		ss.close();
		
		return alarmTotal;
	}
	
	/**
	 * �˶� ������ �Խ��� �Խñ� ��ȯ�ϴ� �޼���
	 * by ���� 190426
	 */
	public List<Alarm> selectAlarms(AlarmBbsVO abv) {
		List<Alarm> list = null;
		
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		list = ss.selectList("selectAlarmBbs", abv);
		ss.close();
		
		return list;
	}
	
	/**
	 * �˶� ����ó�� �޼���
	 * by ���� 190426
	 */
	public void updateReadFlag(String a_num) {
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		int cnt = ss.update("updateReadFlag", a_num);
		ss.commit();
		ss.close();
	}
	
	/**
	 * �˶� �󼼱��� ��ȸ�ϴ� �޼���
	 * by ���� 190426
	 */
	public DetailAlarm selectDetailAlarm(String a_num) {
		DetailAlarm da = null;
		
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		da = ss.selectOne("selectDetailAlarm", a_num);
		ss.close();
		
		return da;
	}
	
	/**
	 * ��ü ���������� ��ȯ�ϴ� �޼���
	 * by ���� 190426
	 */
	public int selectNoticeTotal(String serachWord) {
		int noticeTotal = 0;
		
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		noticeTotal = ss.selectOne("selectNoticeTotal", serachWord);
		ss.close();
		
		return noticeTotal;
	}
	
	/**
	 * �������� �Խñ��� ��ȸ�ϴ� �޼���
	 * by ���� 190426
	 */
	public List<Notice> selectNotice(NoticeBbsVO nbv) {
		List<Notice> list = null;
		
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		list = ss.selectList("selectNoticeBbs", nbv);
		ss.close();
		
		return list;
	}
	
	/**
	 * �������� ��ȸ���� �ø��� �޼���
	 * by ���� 190426
	 */
	public void updateViewCnt(String n_num) {
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		ss.update("updateViewCnt",n_num);
		ss.commit();
		ss.close();
	}
	
	/**
	 * �� ���������� ��ȸ�ϴ� �޼���
	 * by ���� 190426
	 */
	public DetailNotice selectDetailNotice(String n_num) {
		DetailNotice dn = null;
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		dn = ss.selectOne("selectDetailNotice",n_num);
		ss.close();
		return dn;
	}
	
	/**
	 * ���ǻ����� ����ϴ� �޼���
	 * by ���� 190426
	 */
	public boolean insertQuestion(QuestionVO qvo) {
		boolean flag = false;
		
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		int cnt = ss.insert("insertNewQuestion", qvo);
		if (cnt == 1) {
			flag = true;
			ss.commit();
		}
		ss.close();
		
		return flag;
	}
	
	/**
	 * id�� �ش��ϴ� ��ü ���Ǽ��� ��ȯ�ϴ� �޼���
	 * by ���� 190426
	 */
	public int selectQuestionTotal(String id) {
		int qTotal = 0;
		
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		qTotal = ss.selectOne("selectQuestionTotal", id);
		ss.close();
		
		return qTotal;
	}
	
	/**
	 * ���� ���� ���Ǳ��� ��ȸ�ϴ� �޼���
	 * by ���� 190426
	 */
	public List<MyQuestion> selectMyQuestion(QuestionBbsVO qbv) {
		List<MyQuestion> list = null;
		
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		list = ss.selectList("selectMyQuestions", qbv);
		ss.close();
		
		return list; 
	}
	
	/**
	 * �� �� ���Ǳ��� ��ȸ�ϴ� �޼���
	 * by ���� 190426
	 */
	public DetailQuestion selectDetailQuestion(String q_num) {
		DetailQuestion dq = null;
		
		SqlSession ss = CommonBbsDAO.getInstance().getSqlSessionFactory().openSession();
		dq = ss.selectOne("selectDetailQuestion",q_num);
		ss.close();
		
		return dq;
	}
}
