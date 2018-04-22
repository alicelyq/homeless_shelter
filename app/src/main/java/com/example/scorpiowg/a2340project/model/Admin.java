package com.example.scorpiowg.a2340project.model;

/**
 * Created by wangjingbo on 2/17/18.
 */

public class Admin extends User {

    /**
     * Construct a Shelter Employee user type object
     * @param name the name of user
     * @param userId the user id
     * @param password the password of the user account
     */
    public Admin(String name, String userId, String password) {
        super(name, userId, password, true, "admin");
    }

    @Override
    public String toString() {
        return "ADMIN\nname: " + getName() + "\nID: " + getUserId();
    }
}
