package com.example.scorpiowg.a2340project.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangjingbo on 2/17/18.
 */

public class User {

    private final String name;
    private final String userId;
    private final String password;
    private final boolean accountState;
    private String type;
    private Shelter claim;
    private int beds;

    @SuppressWarnings("AssignmentToNull")
    /**
     * Construct a General user type
     * which is the super class of all other type of users
     * @param name the name of user
     * @param userId the user id
     * @param password the password of the user account
     * @param accountState whether this account is blocked
     * @param type the specific user type
     */
    public User(String name, String userId, String password, boolean accountState, String type) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.accountState = accountState;
        this.type = type;
        //noinspection AssignmentToNull
        this.claim = null;
        this.beds = 0;
    }

    /**
     * Getter of the user's name
     * @return a String which is the name of this user
     */
    public String getName() {
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
    public boolean getAccountState() {
        return accountState;
    }

    /**
     * Getter of the current living shelter of this user
     * @return a Shelter object which holds this user
     */
    public Shelter getClaim() {
        return claim;
    }

    /**
     * Change the 
     * @return a String which is the name of this user
     */
    public void setClaim(Shelter shelter) {
        claim = shelter;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }


//    private User(Parcel in) {
//        name = in.readString();
//        userId = in.readString();
//        password = in.readString();
//        accountState = in.readByte() != 0;
//        type = in.readString();
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    /* *************************
//       If you add new instance vars to Student, you will need to add them to the write
//       Be sure the order you write information matches the order that the constructor above
//       reads them.
//     */
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(name);
//        dest.writeString(userId);
//        dest.writeString(password);
//        dest.writeByte((byte) (accountState ? 1 : 0));
//        dest.writeString(type);
//    }
//
//    /**
//     * Should not have to edit this method if the constructor and write method are
//     * working correctly.
//     */
//    public static final Parcelable.Creator<Student> CREATOR
//            = new Parcelable.Creator<Student>() {
//        public Student createFromParcel(Parcel in) {
//            return new Student(in);
//        }
//
//        public Student[] newArray(int size) {
//            return new Student[size];
//        }
//    };
}
