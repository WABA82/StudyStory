package kr.co.studystory.vo;

public class SearchThumbListVO {

	private int startNum, endNum, currentPage;

	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getEndNum() {
		return endNum;
	}

	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	@Override
	public String toString() {
		return "SearchThumbListVO [startNum=" + startNum + ", endNum=" + endNum + ", currentPage=" + currentPage + "]";
	}

}// class
