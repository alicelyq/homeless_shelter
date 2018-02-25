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
    private Map<Integer, String[]> shelterdb;


    /**
     * make a new model
     */
    private Model() {
        database = new HashMap<>();
        shelterdb = new HashMap<>();
    }

    public HashMap getDatabase() {
        return database;
    }
    public void setShelterdb(Map<Integer, String[]> shelterInfo) {
        shelterdb = shelterInfo;
    }
}
