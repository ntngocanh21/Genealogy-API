package com.api.genealogy.controller;

import com.api.genealogy.model.Genealogy;
import com.api.genealogy.service.GenealogyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class GenealogyController {

    @Autowired
    private GenealogyService genealogyService;

    @PostMapping("/genealogy")
    public ResponseEntity createGenealogy(@RequestBody Genealogy genealogy) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(genealogyService.createGenealogy(currentUserName,genealogy), HttpStatus.OK);
    }

    @PutMapping("/genealogy")
    public ResponseEntity updateGenealogy(@RequestBody Genealogy genealogy) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(genealogyService.updateGenealogy(currentUserName,genealogy), HttpStatus.OK);
    }

    @DeleteMapping("/genealogy")
    public ResponseEntity deleteGenealogy(@RequestBody int genealogyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(genealogyService.deleteGenealogy(currentUserName,genealogyId), HttpStatus.OK);
    }


    //test config url
    @GetMapping("/genealogy")
    public String hello() {
        return "helloaa";
    }
}
