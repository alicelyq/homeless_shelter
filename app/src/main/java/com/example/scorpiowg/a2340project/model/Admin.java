package com.example.scorpiowg.a2340project.model;

/**
 * Created by wangjingbo on 2/17/18.
 */

public class Admin extends User {
    public Admin(String name, String userId, String password, String accountState) {
        super(name, userId, password, accountState, "admin");
    }
}
