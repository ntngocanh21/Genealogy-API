package com.api.genealogy.model;

public class Notification {
	
	private Integer id;
	private String title;
	private String type;
	private String content;
	private String deviceId;
	private Integer isPushed;
	private String username;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getIsPushed() {
		return isPushed;
	}

	public void setIsPushed(Integer isPushed) {
		this.isPushed = isPushed;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
    
}
