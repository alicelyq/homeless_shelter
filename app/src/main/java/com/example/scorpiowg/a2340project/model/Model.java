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

    // add in db
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    /**
     * make a new model
     */
    private Model() {
        database = new HashMap<>();
        shelters = new HashMap<>();
    }

    public HashMap getDatabase() {
        return database;
    }


    public HashMap getShelters() {
//        shelters = new HashMap<>();
//        Shelter one = new Shelter("0", "one", "100", null, "long", "lat", "address", "special", "phone number");
//        Shelter two = new Shelter("1", "two", "100", null, "long", "lat", "address", "special", "phone number");
//        shelters.put("0", one);
//        shelters.put("1", two);
        return shelters;
    }

    public void addNewShelter(String shelterId, String name, String capacity, String restriction, String longitude, String latitude, String address, String specialNotes, String phoneNum) {
//            Shelter shelter = new Shelter();
        db.child("shelters").child(shelterId).child("name").setValue(name);
        db.child("shelters").child(shelterId).child("capacity").setValue(capacity);
        db.child("shelters").child(shelterId).child("restriction").setValue(restriction);
        db.child("shelters").child(shelterId).child("longitude").setValue(longitude);
        db.child("shelters").child(shelterId).child("latitude").setValue(latitude);
        db.child("shelters").child(shelterId).child("address").setValue(address);
        db.child("shelters").child(shelterId).child("specialNotes").setValue(specialNotes);
        db.child("shelters").child(shelterId).child("phoneNum").setValue(phoneNum);
    }

    @Override
    public String toString() {
        return "hello";
    }

    public void setShelters(HashMap<String, Shelter> shelterInfo) {
        shelters = shelterInfo;
    }
}
