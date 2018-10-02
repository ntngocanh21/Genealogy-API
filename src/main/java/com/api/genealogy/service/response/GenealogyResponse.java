package com.api.genealogy.service.response;

import com.api.genealogy.model.Genealogy;

import java.util.List;

public class GenealogyResponse {

	private MessageResponse error;
    private List<Genealogy> genealogyList;

    public GenealogyResponse() {
    }

    public GenealogyResponse(MessageResponse error, List<Genealogy> genealogyList) {
        this.error = error;
        this.genealogyList = genealogyList;
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
}
