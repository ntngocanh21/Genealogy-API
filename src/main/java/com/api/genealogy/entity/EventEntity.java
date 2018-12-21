package com.api.genealogy.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private Date date;
    
    @Column(name = "content", columnDefinition="TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id_created", nullable = false)
    public UserEntity userCreatedEventEntity;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    public BranchEntity branchEventEntity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUserCreatedEventEntity() {
        return userCreatedEventEntity;
    }

    public void setUserCreatedEventEntity(UserEntity userCreatedEventEntity) {
        this.userCreatedEventEntity = userCreatedEventEntity;
    }

    public BranchEntity getBranchEventEntity() {
        return branchEventEntity;
    }

    public void setBranchEventEntity(BranchEntity branchEventEntity) {
        this.branchEventEntity = branchEventEntity;
    }
}

