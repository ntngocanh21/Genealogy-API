package com.api.genealogy.service;


import com.api.genealogy.model.*;

public interface GenealogyService {
    GenealogyResponse getGenealogies();
    GenealogyResponse getGenealogiesByUsername(String username);
    CreateResponse createGenealogy(String username, Genealogy genealogy);
    CodeResponse deleteGenealogy(String username, Integer genealogyId);
    CodeResponse updateGenealogy(String username, Genealogy genealogy);
}
