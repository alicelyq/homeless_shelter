package com.example.scorpiowg.a2340project.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangjingbo on 2/17/18.
 */

@SuppressWarnings("ALL")
public class User {

    private final String name;
    private final String userId;
    private final String password;
    private final boolean accountState;
    @SuppressWarnings("FieldCanBeLocal")
    private final String type;
    private Shelter claim;
    private int beds;

    @SuppressWarnings("AssignmentToNull")
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

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public boolean getAccountState() {
        return accountState;
    }

    public Shelter getClaim() {
        return claim;
    }

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
