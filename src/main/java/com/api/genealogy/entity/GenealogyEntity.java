package com.api.genealogy.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "genealogy")
public class GenealogyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "history", columnDefinition="TEXT")
    private String history;

    @Column(name = "date")
    private Date date;

    @Column(name = "branch")
    private Integer branch;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity userEntity;

    @OneToMany(mappedBy = "genealogyEntity", cascade=CascadeType.ALL)
    private List<BranchEntity> branchEntities;

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

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public List<BranchEntity> getBranchEntities() {
        return branchEntities;
    }

    public void setBranchEntities(List<BranchEntity> branchEntities) {
        this.branchEntities = branchEntities;
    }
}

