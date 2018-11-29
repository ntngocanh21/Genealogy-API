package com.api.genealogy.service;


import com.api.genealogy.model.*;
import com.api.genealogy.service.response.CodeResponse;
import com.api.genealogy.service.response.GenealogyResponse;

public interface GenealogyService {
    GenealogyResponse getGenealogies(String username);
    GenealogyResponse getGenealogiesByUsername(String username);
    GenealogyResponse createGenealogy(String username, Genealogy genealogy);
    CodeResponse deleteGenealogy(String username, Integer genealogyId);
    CodeResponse updateGenealogy(String username, Genealogy genealogy);
}
