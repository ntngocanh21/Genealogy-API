package com.api.genealogy.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_branch_permission")
public class UserBranchPermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    public BranchEntity branchUserEntity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity userBranchEntity;

    @ManyToOne
    @JoinColumn(name = "branch_permission_id", nullable = false)
    public BranchPermissionEntity branchPermissionEntity;

    public UserBranchPermissionEntity(){

    }

    public UserBranchPermissionEntity(boolean status, BranchEntity branchUserEntity, UserEntity userBranchEntity, BranchPermissionEntity branchPermissionEntity) {
        this.status = status;
        this.branchUserEntity = branchUserEntity;
        this.userBranchEntity = userBranchEntity;
        this.branchPermissionEntity = branchPermissionEntity;
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

    public BranchEntity getBranchUserEntity() {
        return branchUserEntity;
    }

    public void setBranchUserEntity(BranchEntity branchUserEntity) {
        this.branchUserEntity = branchUserEntity;
    }

    public UserEntity getUserBranchEntity() {
        return userBranchEntity;
    }

    public void setUserBranchEntity(UserEntity userBranchEntity) {
        this.userBranchEntity = userBranchEntity;
    }

    public BranchPermissionEntity getBranchPermissionEntity() {
        return branchPermissionEntity;
    }

    public void setBranchPermissionEntity(BranchPermissionEntity branchPermissionEntity) {
        this.branchPermissionEntity = branchPermissionEntity;
    }
}

