package com.api.genealogy.service.response;

import com.api.genealogy.model.People;

import java.util.List;

public class PeopleResponse {

	private MessageResponse error;
    private List<People> peopleList;

    public PeopleResponse() {
    }

    public PeopleResponse(MessageResponse error, List<People> peopleList) {
        this.error = error;
        this.peopleList = peopleList;
    }

    public MessageResponse getError() {
        return error;
    }

    public void setError(MessageResponse error) {
        this.error = error;
    }

    public List<People> getPeopleList() {
        return peopleList;
    }

    public void setPeopleList(List<People> peopleList) {
        this.peopleList = peopleList;
    }
}
