package com.api.genealogy.service;


import com.api.genealogy.entity.PeopleEntity;
import com.api.genealogy.model.Branch;
import com.api.genealogy.service.response.BranchResponse;
import com.api.genealogy.service.response.CodeResponse;
import com.api.genealogy.service.response.PeopleResponse;

public interface PeopleService {
//    PeopleResponse getPeopleByBranchId(Integer branchId);
//    PeopleResponse createPeople(String username, PeopleEntity peopleEntity);
//    CodeResponse deletePeople(String username, Integer peopleId);
//    CodeResponse updatePeople(String username, PeopleEntity peopleEntity);
    PeopleResponse getPeopleById(Integer peopleId);
}
