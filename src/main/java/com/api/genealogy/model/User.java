package com.api.genealogy.model;

import java.util.Date;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String mail;
    private String fullname;
    private Boolean gender;
    private String address;
    private String avatar;
    private Date birthday;
    private String role;


    public User() {
    }

    public User(Integer id, String username, String password, String mail, String fullname, Boolean gender, String address, String avatar, Date birthday, Date createdDate, Date lastUpdated, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.fullname = fullname;
        this.gender = gender;
        this.address = address;
        this.avatar = avatar;
        this.birthday = birthday;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

}
