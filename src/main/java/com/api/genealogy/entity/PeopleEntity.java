package com.api.genealogy.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "people")
public class PeopleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    public BranchEntity branchEntity;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "gender")
    private int gender;

    @Column(name = "address")
    private String address;

    @Column(name = "death_day")
    private Date deathDay;

    @Column(name = "image")
    private String image;

    @Column(name = "degree")
    private String degree;

    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    @Column(name="life_index", nullable=true)
    private Integer lifeIndex;

    @OneToMany(mappedBy = "parentEntity", cascade=CascadeType.ALL)
    private List<PeopleEntity> peopleEntities;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    public PeopleEntity parentEntity;

    @OneToOne
    @JoinColumn(name = "partner_id", nullable = true)
    private PeopleEntity partnerEntity;

    @OneToOne(mappedBy = "partnerEntity", cascade=CascadeType.ALL)
    private PeopleEntity people;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BranchEntity getBranchEntity() {
        return branchEntity;
    }

    public void setBranchEntity(BranchEntity branchEntity) {
        this.branchEntity = branchEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(Date deathDay) {
        this.deathDay = deathDay;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(Integer lifeIndex) {
        this.lifeIndex = lifeIndex;
    }

    public List<PeopleEntity> getPeopleEntities() {
        return peopleEntities;
    }

    public void setPeopleEntities(List<PeopleEntity> peopleEntities) {
        this.peopleEntities = peopleEntities;
    }

    public PeopleEntity getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(PeopleEntity parentEntity) {
        this.parentEntity = parentEntity;
    }

    public PeopleEntity getPartnerEntity() {
        return partnerEntity;
    }

    public void setPartnerEntity(PeopleEntity partnerEntity) {
        this.partnerEntity = partnerEntity;
    }

}
