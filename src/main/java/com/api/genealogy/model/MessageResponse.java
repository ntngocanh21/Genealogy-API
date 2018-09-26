package com.api.genealogy.model;

public class MessageResponse {
	
    private int code;
    private String description;

    public MessageResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
