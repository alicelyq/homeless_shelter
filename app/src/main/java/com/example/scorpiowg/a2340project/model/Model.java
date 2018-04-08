package com.example.scorpiowg.a2340project.model;

import android.support.compat.BuildConfig;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Model {
    /** Singleton instance */
    private static final Model _instance = new Model();
    public static Model getInstance() { return _instance; }

    /** holds the list of all users */
    private HashMap<String, User> database;

    /** holds the list of all shelters */
    private HashMap<String, Shelter> shelters;

    /** holds the list of shelters after filtering */
    private ArrayList<Shelter> currentShelterList;



    private User user;

    private Shelter shelter;

    // add in db
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    /**
     * make a new model
     */
    private Model() {
        database = new HashMap<>();
        shelters = new HashMap<>();
    }

    public Iterable<Shelter> getCurrentShelterList() {
        if (currentShelterList == null) {
            currentShelterList = new ArrayList<>();
        }
        return currentShelterList;
    }

    public void addCurrentShelter(Shelter s) {
        if (currentShelterList == null) {
            currentShelterList = new ArrayList<>();
        }
        currentShelterList.add(s);
    }

    public void clearCurrentShelterList() {
        if (currentShelterList == null) {
            currentShelterList = new ArrayList<>();
        }
        currentShelterList.clear();
    }

    public HashMap getDatabase() {
        return database;
    }


    public HashMap getShelters() {
        return shelters;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setCurrentShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public void addNewShelter(String shelterId, String name, String capacity, String restriction, String longitude, String latitude, String address, String specialNotes, String phoneNum, int occupied) {
//            Shelter shelter = new Shelter();
        db.child("shelters").child(shelterId).child("name").setValue(name);
        db.child("shelters").child(shelterId).child("capacity").setValue(capacity);
        db.child("shelters").child(shelterId).child("restriction").setValue(restriction);
        db.child("shelters").child(shelterId).child("longitude").setValue(longitude);
        db.child("shelters").child(shelterId).child("latitude").setValue(latitude);
        db.child("shelters").child(shelterId).child("address").setValue(address);
        db.child("shelters").child(shelterId).child("specialNotes").setValue(specialNotes);
        db.child("shelters").child(shelterId).child("phoneNum").setValue(phoneNum);
        db.child("shelters").child(shelterId).child("occupied").setValue(occupied);

    }

    DatabaseReference firebaseUsers = FirebaseDatabase.getInstance().getReference().child("users");

    public void addNewShelterEmployee(String name, String userId, String password, boolean accountState, String shelterId,
                                      Shelter claim, int beds) {
        firebaseUsers.child(userId).child("name").setValue(name);
        firebaseUsers.child(userId).child("userId").setValue(userId);
        firebaseUsers.child(userId).child("password").setValue(password);
        firebaseUsers.child(userId).child("accountState").setValue(accountState);
        firebaseUsers.child(userId).child("shelterId").setValue(shelterId);
        firebaseUsers.child(userId).child("type").setValue("shelterEmployee");
        if (claim != null) {
            firebaseUsers.child(userId).child("claim").setValue(claim.getShelterId());
        } else {
            firebaseUsers.child(userId).child("claim").setValue("null");
        }
        firebaseUsers.child(userId).child("beds").setValue(beds);
    }

    public void addNewAdmin(String name, String userId, String password, boolean accountState,
                            Shelter claim, int beds) {
        firebaseUsers.child(userId).child("name").setValue(name);
        firebaseUsers.child(userId).child("userId").setValue(userId);
        firebaseUsers.child(userId).child("password").setValue(password);
        firebaseUsers.child(userId).child("accountState").setValue(accountState);
        firebaseUsers.child(userId).child("type").setValue("admin");
        if (claim != null) {
            firebaseUsers.child(userId).child("claim").setValue(claim.getShelterId());
        } else {
            firebaseUsers.child(userId).child("claim").setValue("null");
        }
        firebaseUsers.child(userId).child("beds").setValue(beds);
    }

    public void addNewHomeles(String name, String userId, String password, boolean accountState, String govId,
                              String gender, boolean isVeteran, boolean isFamily, int familyNum, int age,
                              Shelter claim, int beds) {
        firebaseUsers.child(userId).child("name").setValue(name);
        firebaseUsers.child(userId).child("userId").setValue(userId);
        firebaseUsers.child(userId).child("password").setValue(password);
        firebaseUsers.child(userId).child("accountState").setValue(accountState);
        firebaseUsers.child(userId).child("govId").setValue(govId);
        firebaseUsers.child(userId).child("gender").setValue(gender);
        firebaseUsers.child(userId).child("isVeteran").setValue(isVeteran);
        firebaseUsers.child(userId).child("isFamily").setValue(isFamily);
        firebaseUsers.child(userId).child("familyNum").setValue(familyNum);
        firebaseUsers.child(userId).child("age").setValue(age);
        firebaseUsers.child(userId).child("type").setValue("homeless");
        if (claim != null) {
            firebaseUsers.child(userId).child("claim").setValue(claim.getShelterId());
        } else {
            firebaseUsers.child(userId).child("claim").setValue("null");
        }
        firebaseUsers.child(userId).child("beds").setValue(beds);


    }


    @Override
    public String toString() {
        return "hello";
    }

    public void setShelters(HashMap<String, Shelter> shelterInfo) {
        shelters = shelterInfo;
    }
}
