package com.api.genealogy.model;

public class Search {
    private String name;

    public Search() {
        super();
    }

    public Search(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
