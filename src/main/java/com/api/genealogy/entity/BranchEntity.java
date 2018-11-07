package com.api.genealogy.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "branch")
public class BranchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    @Basic
    @Column(name = "date")
    private Date date;

    @Basic
    @Column(name = "member")
    private Integer member;

    @ManyToOne
    @JoinColumn(name = "genealogy_id", nullable = false)
    public GenealogyEntity genealogyEntity;

    @OneToMany(mappedBy = "branchEntity", cascade=CascadeType.ALL)
    private List<PeopleEntity> peopleEntities;

    @OneToMany(mappedBy = "branchUserEntity", cascade=CascadeType.ALL)
    private List<UserBranchPermissionEntity> userBranchPermissionEntities;

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

    public GenealogyEntity getGenealogyEntity() {
        return genealogyEntity;
    }

    public void setGenealogyEntity(GenealogyEntity genealogyEntity) {
        this.genealogyEntity = genealogyEntity;
    }

    public List<PeopleEntity> getPeopleEntities() {
        return peopleEntities;
    }

    public void setPeopleEntities(List<PeopleEntity> peopleEntities) {
        this.peopleEntities = peopleEntities;
    }

    public List<UserBranchPermissionEntity> getUserBranchPermissionEntities() {
        return userBranchPermissionEntities;
    }

    public void setUserBranchPermissionEntities(List<UserBranchPermissionEntity> userBranchPermissionEntities) {
        this.userBranchPermissionEntities = userBranchPermissionEntities;
    }
}

