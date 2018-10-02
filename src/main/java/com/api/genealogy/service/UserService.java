package com.api.genealogy.service;


import com.api.genealogy.model.User;
import com.api.genealogy.service.response.LoginResponse;

public interface UserService {
    LoginResponse login(User user);
    LoginResponse register(User user);
}
