package com.example.scorpiowg.a2340project.model;

import android.support.annotation.Nullable;

/**
 * This is the parent class for all type of users.
 * @author Jinbo Wang
 * @version 1.8
 * created on 02/17/2018
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
     * Getter of the current living shelter of this user
     * @return a Shelter object which holds this user
     */
    @Nullable
    public Shelter getClaim() {
        return claim;
    }

    /**
     * Change the shelter claimed by the user
     * @param shelter the new shelter which claimed by this user
     */
    public void setClaim(@Nullable Shelter shelter) {
        claim = shelter;
    }

    /**
     * Getter of the number of beds required by this user
     * @return a int which is the number of requested beds
     */
    public int getBeds() {
        return beds;
    }

    /**
     * Change the number of beds claimed by the user
     * @param beds the number of beds claimed by this user
     */
    public void setBeds(int beds) {
        this.beds = beds;
    }
}
