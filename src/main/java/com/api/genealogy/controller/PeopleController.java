package com.api.genealogy.controller;

import com.api.genealogy.model.People;
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
    public ResponseEntity createPeople(@RequestBody People people) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(peopleService.createPeople(currentUserName,people), HttpStatus.OK);
    }

    @PutMapping("/people")
    public ResponseEntity updatePeople(@RequestBody People people) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(peopleService.updatePeople(currentUserName,people), HttpStatus.OK);
    }

    @DeleteMapping("/people")
    public ResponseEntity deletePeople(@RequestBody int peopleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(peopleService.deletePeople(currentUserName,peopleId), HttpStatus.OK);
    }

    @PostMapping("/people/branch")
    public ResponseEntity getPeopleByBranchId(@RequestBody int branchId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(peopleService.getPeopleByBranchId(branchId), HttpStatus.OK);
    }

}
