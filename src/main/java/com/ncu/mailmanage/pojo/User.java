package com.ncu.mailmanage.pojo;

import java.util.Date;

public class User {
    private Long userId;

    private Date birthday;

    private Date createTime;

    private String gender;

    private String mailAddress;

    private String name;

    private String position;

    private Date updateTime;

    private String avatar;

    private String password;

    private Boolean locked;

    private String introduction;

    public User(Long userId, Date birthday, Date createTime, String gender, String mailAddress, String name, String position, Date updateTime, String avatar, String password, Boolean locked, String introduction) {
        this.userId = userId;
        this.birthday = birthday;
        this.createTime = createTime;
        this.gender = gender;
        this.mailAddress = mailAddress;
        this.name = name;
        this.position = position;
        this.updateTime = updateTime;
        this.avatar = avatar;
        this.password = password;
        this.locked = locked;
        this.introduction = introduction;
    }

    public User() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress == null ? null : mailAddress.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }
}