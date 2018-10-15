package com.api.genealogy.model;


import java.util.Date;

public class Branch {

    private Integer id;
    private String name;
    private String description;
    private Date date;
    private Integer member;
    private Integer genealogyId;

    public Branch() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public Integer getGenealogyId() {
        return genealogyId;
    }

    public void setGenealogyId(Integer genealogyId) {
        this.genealogyId = genealogyId;
    }
}
