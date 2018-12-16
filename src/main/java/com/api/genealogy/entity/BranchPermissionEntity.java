package com.api.genealogy.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "branch_permission")
public class BranchPermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "permission_name")
    private String permissionName;

    @OneToMany(mappedBy = "branchPermissionEntity", cascade=CascadeType.ALL)
    private List<UserBranchPermissionEntity> userBranchPermissionEntities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public List<UserBranchPermissionEntity> getUserBranchPermissionEntities() {
        return userBranchPermissionEntities;
    }

    public void setUserBranchPermissionEntities(List<UserBranchPermissionEntity> userBranchPermissionEntities) {
        this.userBranchPermissionEntities = userBranchPermissionEntities;
    }
}

