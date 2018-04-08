package com.example.scorpiowg.a2340project.model;

/**
 * Created by wangjingbo on 2/17/18.
 */

@SuppressWarnings("ALL")
public class Admin extends User {
    public Admin(String name, String userId, String password, boolean accountState) {
        super(name, userId, password, accountState, "admin");
    }

    public String toString() {
        return "ADMIN\nname: " + getName() + "\nID: " + getUserId();
    }
}
