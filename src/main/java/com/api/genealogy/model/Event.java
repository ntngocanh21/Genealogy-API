package com.api.genealogy.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Event {
    private Integer id;
    private String title;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="Asia/Ho_Chi_Minh")
    private Date date;
    private Integer branch_id;
    private String username;


    public Event() {
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


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Integer getBranch_id() {
		return branch_id;
	}


	public void setBranch_id(Integer branch_id) {
		this.branch_id = branch_id;
	}


	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

}
