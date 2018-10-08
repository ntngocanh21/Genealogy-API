package com.api.genealogy.service;


import com.api.genealogy.model.Branch;
import com.api.genealogy.service.response.BranchResponse;
import com.api.genealogy.service.response.CodeResponse;

public interface BranchService {
    BranchResponse getBranchesByGenealogyId(Integer genealogyId);
    BranchResponse createBranch(String username, Integer genealogyId, Branch branch);
    CodeResponse deleteBranch(String username, Integer branchId);
    CodeResponse updateBranch(String username, Branch branch);
}
