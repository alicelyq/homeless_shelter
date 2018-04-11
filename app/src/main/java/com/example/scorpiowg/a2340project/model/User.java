package com.example.scorpiowg.a2340project.model;

import android.support.annotation.Nullable;

/**
 * Created by wangjingbo on 2/17/18.
 */

public class User {

    private final String name;
    private final String userId;
    private final String password;
    private final String type;
    private final boolean accountState;
    @Nullable
    private Shelter claim;
    private int beds;

    /**
     * Construct a General user type
     * which is the super class of all other type of users
     * @param name the name of user
     * @param userId the user id
     * @param password the password of the user account
     * @param accountState whether this account is blocked
     * @param type the specific user type
     */
    User(String name, String userId, String password, boolean accountState, String type) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.accountState = accountState;
        this.type = type;
        this.claim = null;
        this.beds = 0;
    }

    /**
     * Getter of the user's name
     * @return a String which is the name of this user
     */
    String getName() {
        return name;
    }

    /**
     * Getter of the user's id
     * @return a String which is the id of this user
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Getter of the user's password
     * @return a String which is the password of this user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter of the user's account state
     * @return a boolean which is true when it is not blocked and vice versa
     */
//    public boolean getAccountState() {
//        return accountState;
//    }

    /**
     * Getter of the current living shelter of this user
     * @return a Shelter object which holds this user
     */
    @Nullable
    public Shelter getClaim() {
        return claim;
    }

    /**
     * Change the 
     * @return a String which is the name of this user
     */
    public void setClaim(@Nullable Shelter shelter) {
        claim = shelter;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }
}
