package kr.co.studystory.vo;

/**
 * ���� ���͵� ���� �ϱ� ���� VO.
 * 
 * @author vkfqk
 *
 */
public class RemoveFavStudyVO {

	private String sNum, id;

	public String getsNum() {
		return sNum;
	}

	public void setsNum(String sNum) {
		this.sNum = sNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RemoveFavStudyVO [sNum=" + sNum + ", id=" + id + "]";
	}

}// class
