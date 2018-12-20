package com.api.genealogy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.genealogy.model.Branch;
import com.api.genealogy.model.Event;
import com.api.genealogy.model.Notification;
import com.api.genealogy.model.User;
import com.api.genealogy.service.BranchService;
import com.api.genealogy.service.NotificationService;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;

    @PostMapping("/notification/user")
    public ResponseEntity getListOfNotifications() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(notificationService.getListOfNotifications(currentUserName), HttpStatus.OK);
    }
    
    @PutMapping("/event")
    public ResponseEntity pushEvent(@RequestBody Event event) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(notificationService.pushEvent(event), HttpStatus.OK);
    }
}
