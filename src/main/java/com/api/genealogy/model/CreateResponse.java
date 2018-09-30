package com.api.genealogy.model;

public class CreateResponse {
    private MessageResponse error;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MessageResponse getError() {
        return error;
    }

    public void setError(MessageResponse error) {
        this.error = error;
    }
}
