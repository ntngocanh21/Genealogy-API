package com.api.genealogy.controller;

import com.api.genealogy.model.User;
import com.api.genealogy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@RestController()
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user) {
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }
}
