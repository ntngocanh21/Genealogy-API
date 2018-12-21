package com.api.genealogy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.api.genealogy.service.NotificationService;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;

    @GetMapping("/notification/user")
    public ResponseEntity getListOfNotifications() {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(notificationService.getListOfNotifications(currentUserName), HttpStatus.OK);
    }
}
