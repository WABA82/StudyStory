package kr.co.studystory.vo;

/**
 * ���� ���ƿ��� ��������� ���� VO.
 * 
 * @author owner
 *
 */
public class FavSNumFlagVO {

	private String id, myFavSNum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMyFavSNum() {
		return myFavSNum;
	}

	public void setMyFavSNum(String myFavSNum) {
		this.myFavSNum = myFavSNum;
	}

	@Override
	public String toString() {
		return "FavSNumFlagVO [id=" + id + ", myFavSNum=" + myFavSNum + "]";
	}// toString

}// class
