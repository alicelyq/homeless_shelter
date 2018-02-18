package com.example.scorpiowg.a2340project.model;

import android.support.compat.BuildConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Model {
    /** Singleton instance */
    private static final Model _instance = new Model();
    public static Model getInstance() { return _instance; }

    /** holds the list of all courses */
    private HashMap<String, User> database;

    /**
     * make a new model
     */
    private Model() {
        database = new HashMap<>();
    }

    public HashMap getDatabase() {
        return database;
    }
}
