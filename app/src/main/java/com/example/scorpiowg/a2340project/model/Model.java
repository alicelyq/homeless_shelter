package com.example.scorpiowg.a2340project.model;

import android.support.compat.BuildConfig;

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

    private User user;

    private Shelter shelter;


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

    @Override
    public String toString() {
        return "hello";
    }

    public void setShelters(HashMap<String, Shelter> shelterInfo) {
        shelters = shelterInfo;
    }
}
