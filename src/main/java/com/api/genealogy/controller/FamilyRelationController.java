package com.api.genealogy.controller;

import com.api.genealogy.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class FamilyRelationController {

    @Autowired
    private PeopleService peopleService;

    @PostMapping("/familyRelation")
    public ResponseEntity getFamilyRelation(@RequestBody int peopleId) {
        return new ResponseEntity<>(peopleService.getFamilyRelation(peopleId), HttpStatus.OK);
    }
}
