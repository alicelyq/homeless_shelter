package com.example.scorpiowg.a2340project.model;


/**
 * Created by wangjingbo on 2/17/18.
 */

public class Homeless extends User {

    private final String govId;
    private final String gender;
    private final boolean isVeteran;
    private final boolean isFamily;
    private final int familyNum;
    private final int age;

    public Homeless(String name, String userId, String password, boolean accountState, String govId,
                    String gender, boolean isVeteran, boolean isFamily, int familyNum, int age) {
        super(name, userId, password, accountState, "homeless");
        this.govId = govId;
        this.gender = gender;
        this.isVeteran = isVeteran;
        this.isFamily = isFamily;
        this.familyNum = familyNum;
        this.age = age;
    }

    public String getGovId() {
        return govId;
    }

    public String getGender() {
        return gender;
    }

    public boolean getIsVeteran() {
        return isVeteran;
    }

    public boolean getIsFamily() {
        return isFamily;
    }

    public int getFamilyNum() {
        return familyNum;
    }

    public int getAge() {
        return age;
    }

    public String toString() {
        return "HOMELESS\nname: " + getName() + "\nID: " + getUserId() + "\nGOV ID: " + getGovId() + "\ngender: " + getGender()
                + "\nveteran: " + getIsVeteran() + "\nisFamily: " + getIsFamily() + "\nfamily number: " + getFamilyNum() + "\nage: " + getAge();
    }
}
