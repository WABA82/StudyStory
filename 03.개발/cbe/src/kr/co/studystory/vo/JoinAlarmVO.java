package kr.co.studystory.vo;

/**
 * ���� ��û�� ���������� �μ�Ʈ �Ǿ��� ��. ALARM ���̺� �μ�Ʈ �ϱ� ���� ������ ������ VO
 * 
 * @author owner
 *
 */
public class JoinAlarmVO {

	private String content, studyName;

	/* setter & getter */

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	/* setter & getter */

	@Override
	public String toString() {
		return "JoinAlarmVO [content=" + content + ", studyName=" + studyName + "]";
	}// toString

}// class
