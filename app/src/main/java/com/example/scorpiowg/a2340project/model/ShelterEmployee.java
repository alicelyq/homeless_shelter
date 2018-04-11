package com.example.scorpiowg.a2340project.model;

/**
 * Created by wangjingbo on 2/17/18.
 */

public class ShelterEmployee extends User {
    private final String shelterId;

    /**
     * Construct a Shelter Employee user type object
     * @param name the name of user
     * @param userId the user id
     * @param password the password of the user account
     * @param accountState whether this account is blocked
     * @param shelterId the id of the shelter to which this employee belongs
     */
    public ShelterEmployee(String name, String userId, String password
            , boolean accountState, String shelterId) {
        super(name, userId, password, accountState, "shelterEmployee");
        this.shelterId = shelterId;
    }

    /**
     * Getter of the id of the shelter to which this employee belongs
     * @return a String which is the shelter id
     */
    public String getShelterId() {
        return shelterId;
    }

    @Override
    public String toString() {
        return "SHELTER EMPLOYEE\nname: " + getName() + "\nID: " + getUserId()
                + "\nshelterID: " + getShelterId();
    }
}
