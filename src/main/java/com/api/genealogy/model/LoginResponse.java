package com.api.genealogy.model;

public class LoginResponse {
	
	private MessageResponse error;
    private String token;

    public LoginResponse(MessageResponse error, String token) {
        this.error = error;
        this.token = token;
    }

    public MessageResponse getError() {
        return error;
    }

    public void setMessage(MessageResponse message) {
        this.error = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
