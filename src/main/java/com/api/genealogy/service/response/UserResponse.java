package com.api.genealogy.service.response;

import com.api.genealogy.model.User;

import java.util.List;

public class UserResponse {

	private MessageResponse error;
    private List<User> userList;

    public UserResponse() {
    }

    public MessageResponse getError() {
        return error;
    }

    public void setError(MessageResponse error) {
        this.error = error;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
