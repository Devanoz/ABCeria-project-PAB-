package com.example.abceria.model.user;

import org.jetbrains.annotations.NotNull;

public class User {
    private String id;
    private String username;
    private String password;
    private UserDetail userDetail;

    public User(String id, String username, String password, UserDetail userDetail) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userDetail = userDetail;
    }

    public User(){

    }

    public User(@NotNull String s, @NotNull String s1, @NotNull String s2) {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
}
