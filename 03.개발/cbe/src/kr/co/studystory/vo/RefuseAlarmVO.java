package kr.co.studystory.vo;

public class RefuseAlarmVO {
	
private String category, subject, content, id;
	
	public RefuseAlarmVO(String category, String subject, String content, String id) {
		this.category = category;
		this.subject = subject;
		this.content = content;
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
