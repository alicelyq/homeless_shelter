package com.example.scorpiowg.a2340project.model;

/**
 * This is the parent class for all type of users.
 * @author Jinbo Wang
 * @version 1.8
 * created on 02/17/2018
 */

public class Homeless extends User {

    private final String govId;
    private final String gender;
    private final boolean isVeteran;
    private final boolean isFamily;
    private final int familyNum;
    private final int age;

    /**
     * Construct a Homeless user type object
     * @param name the name of user
     * @param userId the user id
     * @param password the password of the user account
     * @param accountState whether this account is blocked
     * @param govId the government id of the user
     * @param gender the gender information
     * @param isVeteran whether is homeless user is veteran
     * @param isFamily whether the homeless user belongs to a family
     * @param familyNum how many family numbers do this user has
     * @param age the age of this user
     */
    public Homeless(String name, String userId, String password, String govId, boolean accountState,
                    String gender, boolean isVeteran, boolean isFamily, int familyNum, int age) {
        super(name, userId, password, accountState, "homeless");
        this.govId = govId;
        this.gender = gender;
        this.isVeteran = isVeteran;
        this.isFamily = isFamily;
        this.familyNum = familyNum;
        this.age = age;
    }

    /**
     * Getter of the user's government id
     * @return a String which is the government of this user
     */
    public String getGovId() {
        return govId;
    }

    /**
     * Getter of the user's gender
     * @return a String which is the gender of this user
     */
    public String getGender() {
        return gender;
    }

    /**
     * Getter of whether this homeless user is veteran
     * @return a boolean which will be true if this homeless is veteran
     */
    public boolean getIsVeteran() {
        return isVeteran;
    }

    /**
     * Getter of the user's family state
     * @return a boolean which will be true if the user belongs to a family
     */
    public boolean getIsFamily() {
        return isFamily;
    }

    /**
     * Getter of the number of user's family members
     * @return a int which is the number of the homeless user's family members
     */
    public int getFamilyNum() {
        return familyNum;
    }

    /**
     * Getter of the user's age
     * @return a int which is the age of this homeless user
     */
    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "HOMELESS\nname: " + getName() + "\nID: " + getUserId()
                + "\nGOV ID: " + getGovId() + "\ngender: " + getGender()
                + "\nveteran: " + getIsVeteran() + "\nisFamily: " + getIsFamily()
                + "\nfamily number: " + getFamilyNum() + "\nage: " + getAge();
    }
}
