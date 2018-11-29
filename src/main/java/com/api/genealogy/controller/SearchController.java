package com.api.genealogy.controller;
import com.api.genealogy.model.People;
import com.api.genealogy.model.Search;
import com.api.genealogy.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/search/genealogy")
    public ResponseEntity searchGenealogyByName(@RequestBody Search search) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(searchService.searchGenealogyByName(search, currentUserName), HttpStatus.OK);
    }

    @PostMapping("/search/branch")
    public ResponseEntity searchBranchByName(@RequestBody Search search) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(searchService.searchBranchByName(search, currentUserName), HttpStatus.OK);
    }

    @PostMapping("/search/people")
    public ResponseEntity searchBranchByPeople(@RequestBody People people) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(searchService.searchGenealogyByPeople(people, currentUserName), HttpStatus.OK);
    }
}
