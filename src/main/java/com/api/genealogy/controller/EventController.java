package com.api.genealogy.controller;

import com.api.genealogy.model.Event;
import com.api.genealogy.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class EventController {
	
	@Autowired
	private EventService eventService;

    @PostMapping("/event")
    public ResponseEntity pushEvent(@RequestBody Event event) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(eventService.pushEvent(event, currentUserName), HttpStatus.OK);
    }

    @PostMapping("/event/user")
    public ResponseEntity getCreatedEvent(@RequestBody Integer branchId) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(eventService.getCreatedEvent(currentUserName, branchId), HttpStatus.OK);
    }

    @PostMapping("/event/branch")
    public ResponseEntity getEvents(@RequestBody Integer branchId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(eventService.getEvents(branchId), HttpStatus.OK);
    }

}
