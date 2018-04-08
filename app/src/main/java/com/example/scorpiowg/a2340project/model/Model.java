package com.example.scorpiowg.a2340project.model;

import android.support.compat.BuildConfig;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class Model {
    /** Singleton instance */
    private static final Model _instance = new Model();
    public static Model getInstance() { return _instance; }

    /** holds the list of all users */
    private final HashMap<String, User> database;

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
        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
        db.child("shelters").child(shelterId).child("name").setValue(name);
        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
        db.child("shelters").child(shelterId).child("capacity").setValue(capacity);
        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
        db.child("shelters").child(shelterId).child("restriction").setValue(restriction);
        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
        db.child("shelters").child(shelterId).child("longitude").setValue(longitude);
        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
        db.child("shelters").child(shelterId).child("latitude").setValue(latitude);
        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
        db.child("shelters").child(shelterId).child("address").setValue(address);
        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
        db.child("shelters").child(shelterId).child("specialNotes").setValue(specialNotes);
        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
        db.child("shelters").child(shelterId).child("phoneNum").setValue(phoneNum);
        //noinspection ChainedMethodCall,ChainedMethodCall,ChainedMethodCall
        db.child("shelters").child(shelterId).child("occupied").setValue(occupied);

    }

    DatabaseReference firebaseUsers = FirebaseDatabase.getInstance().getReference().child("users");

    public void addNewShelterEmployee(String name, String userId, String password, boolean accountState, String shelterId,
                                      Shelter claim, int beds) {
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("name").setValue(name);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("userId").setValue(userId);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("password").setValue(password);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("accountState").setValue(accountState);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("shelterId").setValue(shelterId);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("type").setValue("shelterEmployee");
        if (claim != null) {
            //noinspection ChainedMethodCall,ChainedMethodCall
            firebaseUsers.child(userId).child("claim").setValue(claim.getShelterId());
        } else {
            //noinspection ChainedMethodCall,ChainedMethodCall
            firebaseUsers.child(userId).child("claim").setValue("null");
        }
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("beds").setValue(beds);
    }

    public void addNewAdmin(String name, String userId, String password, boolean accountState,
                            Shelter claim, int beds) {
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("name").setValue(name);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("userId").setValue(userId);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("password").setValue(password);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("accountState").setValue(accountState);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("type").setValue("admin");
        if (claim != null) {
            //noinspection ChainedMethodCall,ChainedMethodCall
            firebaseUsers.child(userId).child("claim").setValue(claim.getShelterId());
        } else {
            //noinspection ChainedMethodCall,ChainedMethodCall
            firebaseUsers.child(userId).child("claim").setValue("null");
        }
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("beds").setValue(beds);
    }

    public void addNewHomeles(String name, String userId, String password, boolean accountState, String govId,
                              String gender, boolean isVeteran, boolean isFamily, int familyNum, int age,
                              Shelter claim, int beds) {
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("name").setValue(name);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("userId").setValue(userId);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("password").setValue(password);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("accountState").setValue(accountState);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("govId").setValue(govId);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("gender").setValue(gender);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("isVeteran").setValue(isVeteran);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("isFamily").setValue(isFamily);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("familyNum").setValue(familyNum);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("age").setValue(age);
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("type").setValue("homeless");
        if (claim != null) {
            //noinspection ChainedMethodCall,ChainedMethodCall
            firebaseUsers.child(userId).child("claim").setValue(claim.getShelterId());
        } else {
            //noinspection ChainedMethodCall,ChainedMethodCall
            firebaseUsers.child(userId).child("claim").setValue("null");
        }
        //noinspection ChainedMethodCall,ChainedMethodCall
        firebaseUsers.child(userId).child("beds").setValue(beds);


    }


    @Override
    public String toString() {
        return "hello";
    }

    public void setShelters(HashMap<String, Shelter> shelterInfo) {
        //noinspection AssignmentToCollectionOrArrayFieldFromParameter
        shelters = shelterInfo;
    }
}
