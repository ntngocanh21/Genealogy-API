package com.api.genealogy.service;


import com.api.genealogy.model.User;
import com.api.genealogy.service.response.LoginResponse;
import com.api.genealogy.service.response.UserResponse;

public interface UserService {
    LoginResponse login(User user);
    LoginResponse register(User user);
    UserResponse getProfile(String currentUserName);
    UserResponse editProfile(String currentUserName, User user);
	String getDeviceIdFromUsername(String username);
	void updateDeviceId(String deviceId, String userName);
}
