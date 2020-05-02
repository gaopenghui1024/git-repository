package com.work.iwms.entity;

import javax.persistence.*;

@Entity
@Table(name="sys_user")
public class SysUser {
    @Id
    // unique:唯一标识,nullable:不允许为null
    @Column(unique = true, nullable = false, length = 80, name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String passWord;
    @Column(name="useable")
    private String useable;
    @Column(name="create_time")
    private String createTime;

    public SysUser() {
    }

    public SysUser(String userId, String userName, String passWord, String useable, String createTime) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.useable = useable;
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", useable='" + useable + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
