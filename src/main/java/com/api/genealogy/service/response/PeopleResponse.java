package com.api.genealogy.service.response;

import com.api.genealogy.entity.PeopleEntity;
import com.api.genealogy.model.Branch;

import java.util.List;

public class PeopleResponse {

	private MessageResponse error;
    private List<PeopleEntity> peopleEntityList;

    public PeopleResponse() {
    }

    public PeopleResponse(MessageResponse error, List<PeopleEntity> peopleEntityList) {
        this.error = error;
        this.peopleEntityList = peopleEntityList;
    }

    public PeopleResponse(MessageResponse error, PeopleEntity peopleEntity) {
    }

    public MessageResponse getError() {
        return error;
    }

    public void setError(MessageResponse error) {
        this.error = error;
    }

    public List<PeopleEntity> getPeopleEntityList() {
        return peopleEntityList;
    }

    public void setPeopleEntityList(List<PeopleEntity> peopleEntityList) {
        this.peopleEntityList = peopleEntityList;
    }
}
