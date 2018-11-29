package com.api.genealogy.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "role")
    private String role;

    @Basic
    @Column(name = "mail")
    private String mail;

    @Basic
    @Column(name = "fullname")
    private String fullname;

    @Basic
    @Column(name = "gender")
    private Boolean gender;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "avatar")
    private String avatar;

    @Basic
    @Column(name = "birthday")
    private Date birthday;
    
    @Basic
    @Column(name = "device_id")
    private String deviceId;

    @OneToMany(mappedBy = "userBranchEntity", cascade=CascadeType.ALL)
    private List<UserBranchPermissionEntity> userBranchPermissionEntities;

    @OneToMany(mappedBy = "userEntity", cascade=CascadeType.ALL)
    private List<GenealogyEntity> genealogyEntities;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<GenealogyEntity> getGenealogyEntities() {
        return genealogyEntities;
    }

    public void setGenealogyEntities(List<GenealogyEntity> genealogyEntities) {
        this.genealogyEntities = genealogyEntities;
    }

    public List<UserBranchPermissionEntity> getUserBranchPermissionEntities() {
        return userBranchPermissionEntities;
    }

    public void setUserBranchPermissionEntities(List<UserBranchPermissionEntity> userBranchPermissionEntities) {
        this.userBranchPermissionEntities = userBranchPermissionEntities;
    }
    
    public String getDeviceId() {
		return deviceId;
	}
    
    public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}

