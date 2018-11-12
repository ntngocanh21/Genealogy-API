package com.api.genealogy.controller;

import com.api.genealogy.model.Genealogy;
import com.api.genealogy.model.User;
import com.api.genealogy.model.UserBranchPermission;
import com.api.genealogy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(userService.getProfile(currentUserName), HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity editProfile(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(userService.editProfile(currentUserName,user), HttpStatus.OK);
    }
}
