package kr.co.studystory.vo;

/**
 * �� �������� ����� �߰��ϱ� ���� ���Ǵ� VO.
 * 
 * @author owner
 *
 */
public class ReplyVO {

	private String reply, id, sNum;

	/* setter & getter */

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getsNum() {
		return sNum;
	}

	public void setsNum(String sNum) {
		this.sNum = sNum;
	}

	/* setter & getter */

	@Override
	public String toString() {
		return "ReplyVO [reply=" + reply + ", id=" + id + ", sNum=" + sNum + "]";
	}

}// class
