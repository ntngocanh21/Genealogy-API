package com.api.genealogy.controller;

import com.api.genealogy.entity.PeopleEntity;
import com.api.genealogy.model.People;
import com.api.genealogy.repository.PeopleRepository;
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

    @Autowired
    private PeopleRepository peopleRepository;

    @PostMapping("/familyRelation")
    public ResponseEntity getFamilyRelation(@RequestBody int peopleId) {
        return new ResponseEntity<>(peopleService.getFamilyRelation(peopleId), HttpStatus.OK);
    }

    @GetMapping("/familyRelation")
    public ResponseEntity getFamilyRelation() {
        PeopleEntity people1 = peopleRepository.findPeopleEntityById(93);
        PeopleEntity people2 = peopleRepository.findPeopleEntityById(94);
        PeopleEntity people3 = peopleRepository.findPeopleEntityById(95);

        if (people1.getBirthday().after(people2.getBirthday())) {
            System.out.println("Date1 is after Date2");
        } else {
            System.out.println("check1");
        }

        if (people1.getBirthday().before(people2.getBirthday())) {
            System.out.println("Date1 is before Date2");
        } else {
            System.out.println("check2");
        }

        if (people2.getBirthday().after(people3.getBirthday())) {
            System.out.println("Date2 is after Date3");
        } else {
            System.out.println("check3");
        }

        if (people3.getBirthday().before(people2.getBirthday())) {
            System.out.println("Date3 is before Date2");
        } else {
            System.out.println("Date3 is after Date2");
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
