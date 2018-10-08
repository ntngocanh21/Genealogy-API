package com.api.genealogy.service.response;

import com.api.genealogy.model.Branch;

import java.util.List;

public class BranchResponse {

	private MessageResponse error;
    private List<Branch> branchList;

    public BranchResponse() {
    }

    public BranchResponse(MessageResponse error, List<Branch> branchList) {
        this.error = error;
        this.branchList = branchList;
    }

    public MessageResponse getError() {
        return error;
    }

    public void setError(MessageResponse error) {
        this.error = error;
    }

    public List<Branch> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<Branch> branchList) {
        this.branchList = branchList;
    }
}
