package com.api.genealogy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.genealogy.model.Branch;
import com.api.genealogy.model.Notification;
import com.api.genealogy.service.BranchService;
import com.api.genealogy.service.NotificationService;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;

    @PostMapping("/notification")
    public ResponseEntity getListOfNotifications(@RequestBody Notification notification) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(notificationService.getListOfNotifications(currentUserName, notification), HttpStatus.OK);
    }
}
