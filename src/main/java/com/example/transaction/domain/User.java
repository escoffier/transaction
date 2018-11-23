package com.example.transaction.domain;

import org.apache.ibatis.type.Alias;

@Alias(value = "user")
public class User {

    private Long id;

    private String userName = null;

    private String password = null;

    private String nickName = null;

    private String note = null;

    public User() {
        System.out.println("--------Construct User");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}