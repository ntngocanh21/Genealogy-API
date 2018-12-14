package com.api.genealogy.model;

public class Notification {
	
	private Integer id;

    private String title;

    private String content;

    private Boolean readStatus;

    public Integer user_id;

    public Integer notification_type_id;

    public Notification() {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Boolean readStatus) {
		this.readStatus = readStatus;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getNotification_type_id() {
		return notification_type_id;
	}

	public void setNotification_type_id(Integer notification_type_id) {
		this.notification_type_id = notification_type_id;
	}
}
