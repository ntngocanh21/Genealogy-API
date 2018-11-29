package com.api.genealogy.service;

import com.api.genealogy.model.People;
import com.api.genealogy.model.Search;
import com.api.genealogy.service.response.BranchResponse;
import com.api.genealogy.service.response.GenealogyResponse;
import com.api.genealogy.service.response.PeopleResponse;

public interface SearchService {
    GenealogyResponse searchGenealogyByName(Search search, String username);
    BranchResponse searchBranchByName(Search search, String username);
    PeopleResponse searchBranchByPeople(People people, String username);
}
