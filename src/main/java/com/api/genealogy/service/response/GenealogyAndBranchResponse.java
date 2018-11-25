package com.api.genealogy.service.response;

import com.api.genealogy.model.Branch;
import com.api.genealogy.model.Genealogy;

import java.util.List;

public class GenealogyAndBranchResponse {

	private MessageResponse error;
    private List<Genealogy> genealogyList;
    private List<Branch> branchList;

    public GenealogyAndBranchResponse() {
    }

    public MessageResponse getError() {
        return error;
    }

    public void setError(MessageResponse error) {
        this.error = error;
    }

    public List<Genealogy> getGenealogyList() {
        return genealogyList;
    }

    public void setGenealogyList(List<Genealogy> genealogyList) {
        this.genealogyList = genealogyList;
    }

    public List<Branch> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<Branch> branchList) {
        this.branchList = branchList;
    }
}
