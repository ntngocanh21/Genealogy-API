package com.api.genealogy.service.response;

public class LoginResponse {
	
	private MessageResponse error;
    private String token;
    private String avatar;
    private String fullname;
    private String branchId;

    public LoginResponse(MessageResponse error, String token, String avatar, String fullname, String branchId) {
        this.error = error;
        this.token = token;
        this.avatar = avatar;
        this.fullname = fullname;
        this.branchId = branchId;
    }

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
    
    public String getBranchId() {
		return branchId;
	}
    
    public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

    public void setToken(String token) {
        this.token = token;
    }

    public void setError(MessageResponse error) {
        this.error = error;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
