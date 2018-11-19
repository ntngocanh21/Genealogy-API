package com.api.genealogy.service;

import com.api.genealogy.model.People;
import com.api.genealogy.model.Search;
import com.api.genealogy.service.response.GenealogyAndBranchResponse;
import com.api.genealogy.service.response.PeopleResponse;

public interface SearchService {
    GenealogyAndBranchResponse searchGenealogyByName(Search search);
    PeopleResponse searchGenealogyByPeople(People people);
}
