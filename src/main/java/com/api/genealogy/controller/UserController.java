package com.api.genealogy.controller;

import com.api.genealogy.model.UserBranchPermission;
import com.api.genealogy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

}
