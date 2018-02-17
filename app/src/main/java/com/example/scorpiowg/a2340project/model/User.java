package com.example.scorpiowg.a2340project.model;

/**
 * Created by wangjingbo on 2/17/18.
 */

public class User {

    private String name;
    private String userId;
    private String password;
    private String accountState;
    private String type;

    public User(String name, String userId, String password, String accountState, String type) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.accountState = accountState;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountState() {
        return accountState;
    }
}
