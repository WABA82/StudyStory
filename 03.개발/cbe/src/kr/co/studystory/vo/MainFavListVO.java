package kr.co.studystory.vo;

/**
 * ���� �������� �α���� ���۰� �� �ޱ�.
 * 
 * @author vkfqk
 *
 */
public class MainFavListVO {

	private int favCurPage, favStartNum, favEndNum;

	public int getFavCurPage() {
		return favCurPage;
	}

	public void setFavCurPage(int favCurPage) {
		this.favCurPage = favCurPage;
	}

	public int getFavStartNum() {
		return favStartNum;
	}

	public void setFavStartNum(int favStartNum) {
		this.favStartNum = favStartNum;
	}

	public int getFavEndNum() {
		return favEndNum;
	}

	public void setFavEndNum(int favEndNum) {
		this.favEndNum = favEndNum;
	}

	@Override
	public String toString() {
		return "MainFavListVO [favCurPage=" + favCurPage + ", favStartNum=" + favStartNum + ", favEndNum=" + favEndNum
				+ "]";
	}

}// class
