package com.example.scorpiowg.a2340project.model;


/**
 * Created by wangjingbo on 2/17/18.
 */

public class Homeless extends User {

    private String govId;
    private String gender;
    private boolean isVeteran;
    private boolean isFamily;
    private int familyNum;
    private int age;

    public Homeless(String name, String userId, String password, String accountState, String govId, String gender, boolean isVeteran, boolean isFamily, int familyNum, int age) {
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
}
