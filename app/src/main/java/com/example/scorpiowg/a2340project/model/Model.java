package com.example.scorpiowg.a2340project.model;

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
    private final Map<String, User> database;

    /** holds the list of all shelters */
    private Map<String, Shelter> shelters;

    /** holds the list of shelters after filtering */
    private List<Shelter> currentShelterList;

    private User user;

    private Shelter shelter;

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

    public Map getDatabase() {
        return database;
    }


    public Map getShelters() {
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



    /** logic */
    public boolean filterByGender(String gender, String constraint) {
        if ("Any".equals(gender)) {
            return true;
        } else if (constraint.contains(gender)) {
                return true;
        }
        return false;
    }

    public boolean loginUser(Map<String, User> database, String userId, String password) {
        if ((database.get(userId) != null) && database.get(userId).getPassword().equals(password)) {
            Model.getInstance().setUser(database.get(userId));
            return true;
        } else {
            return false;
        }
    }


    public boolean filterByAge(String ageRange, String constraint) {
        if ("Anyone".equals(ageRange)) {
            return true;
        } else {
            if (constraint.toLowerCase().contains(ageRange.toLowerCase())) {
                return true;
            } else if ("Families with newborns".equals(ageRange) && constraint.contains("Families w")) {
                return true;
            }
            return false;
        }
    }

    public boolean filterByName(String sheltername, Shelter currentShelter) {
        return currentShelter.getName().toLowerCase().contains(sheltername.toLowerCase());
    }

    public boolean registerUser(Map<String, User> database, String userId, String password, String confirm,
                                String username, boolean accountState, String shelterId) {
        if (password.equals(confirm) && !database.containsKey(userId)) {
            User curUser = new ShelterEmployee(username, userId, password, true, "1");
            database.put(userId, curUser);
            return true;
        }
        return false;
    }

            

    /** database work */
    public void addNewShelter(String shelterId, String name, String capacity, String restriction, String longitude, String latitude, String address, String specialNotes, String phoneNum, int occupied) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

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


    public void addNewShelterEmployee(String name, String userId, String password, boolean accountState, String shelterId,
                                      Shelter claim, int beds) {

        DatabaseReference firebaseUsers = FirebaseDatabase.getInstance().getReference().child("users");

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
        DatabaseReference firebaseUsers = FirebaseDatabase.getInstance().getReference().child("users");

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
        DatabaseReference firebaseUsers = FirebaseDatabase.getInstance().getReference().child("users");

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

    public void setShelters(Map<String, Shelter> shelterInfo) {
        shelters = new HashMap<>();
        for (String key: shelterInfo.keySet()) {
            shelters.put(key, shelterInfo.get(key));
        }
    }
}
