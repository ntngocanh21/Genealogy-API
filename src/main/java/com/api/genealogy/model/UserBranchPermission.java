package com.api.genealogy.model;

public class UserBranchPermission {

    private Integer id;
    private boolean status;
    private String username;
    private Integer branch_id;
    private Integer branch_permission_id;

    public UserBranchPermission() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }

    public Integer getBranch_permission_id() {
        return branch_permission_id;
    }

    public void setBranch_permission_id(Integer branch_permission_id) {
        this.branch_permission_id = branch_permission_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
