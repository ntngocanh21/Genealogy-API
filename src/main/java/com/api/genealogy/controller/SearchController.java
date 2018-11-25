package com.api.genealogy.controller;
import com.api.genealogy.model.People;
import com.api.genealogy.model.Search;
import com.api.genealogy.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/search/name")
    public ResponseEntity searchGenealogyByName(@RequestBody Search search) {
        return new ResponseEntity<>(searchService.searchGenealogyByName(search), HttpStatus.OK);
    }

    @PostMapping("/search/people")
    public ResponseEntity searchGenealogyByPeople(@RequestBody People people) {
        return new ResponseEntity<>(searchService.searchGenealogyByPeople(people), HttpStatus.OK);
    }
}
