package com.api.genealogy.service;

import com.api.genealogy.model.People;
import com.api.genealogy.service.response.CodeResponse;
import com.api.genealogy.service.response.PeopleResponse;

public interface PeopleService {
    PeopleResponse getPeopleByBranchId(Integer branchId);
    PeopleResponse createPeople(String username, People people);
    CodeResponse deletePeople(String username, Integer peopleId);
    CodeResponse updatePeople(String username, People people);

    PeopleResponse getFamilyRelation(int peopleId);
    PeopleResponse getAllPeopleFromSystem();
    
}
