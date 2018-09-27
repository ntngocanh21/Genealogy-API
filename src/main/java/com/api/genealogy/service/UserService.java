package com.api.genealogy.service;


import com.api.genealogy.model.LoginResponse;
import com.api.genealogy.model.User;

public interface UserService {
    LoginResponse login(User user);
    LoginResponse register(User user);
}
