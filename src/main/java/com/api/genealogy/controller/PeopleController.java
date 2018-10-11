package com.api.genealogy.controller;

import com.api.genealogy.model.Branch;
import com.api.genealogy.service.BranchService;
import com.api.genealogy.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @PostMapping("/people")
    public ResponseEntity getPeople(@RequestBody int peopleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(peopleService.getPeopleById(peopleId), HttpStatus.OK);
    }


}
