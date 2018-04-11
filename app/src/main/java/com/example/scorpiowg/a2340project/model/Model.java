package com.example.scorpiowg.a2340project.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the Singleton class for the project.
 * @version 1.8
 */

public final class Model {
    // Singleton instance
    private static final Model _instance = new Model();

    /**
     * Retrieve the Singleton instance
     * @return a Model object which is the singleton instance
     */
    public static Model getInstance() { return _instance; }

    // holds the list of all users
    private final Map<String, User> database;

    // holds the list of all shelters
    private Map<String, Shelter> shelters;

    // holds the list of shelters after filtering
    private List<Shelter> currentShelterList;

    private User user;

    private Shelter shelter;

    //make a new model
    private Model() {
        database = new HashMap<>();
        shelters = new HashMap<>();
    }

    /**
     * Return the current shelter list
     * @return an Iterable object containing all current shelters
     */
    public Iterable<Shelter> getCurrentShelterList() {

        if (currentShelterList == null) {
            currentShelterList = new ArrayList<>();
        }
        return currentShelterList;
    }

    /**
     * Add a shelter to the current shelter list
     * @param s a Shelter type of object which is the added shelter
     */
    public void addCurrentShelter(Shelter s) {
        if (currentShelterList == null) {
            currentShelterList = new ArrayList<>();
        }
        currentShelterList.add(s);
    }

    /**
     * Clean the current shelter list
     */
    public void clearCurrentShelterList() {
        if (currentShelterList == null) {
            currentShelterList = new ArrayList<>();
        }
        currentShelterList.clear();
    }

    /**
     * Retrieve the database
     * @return a Map object which is the database
     */
    public Map getDatabase() {
        return database;
    }

    /**
     * Retrieve wanted shelters
     * @return a Map object containing all wanted shelters
     */
    public Map getShelters() {
        return shelters;
    }

    /**
     * Access a current running user
     * @return a User object which is the current running user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the current running user
     * @param user a User object which would be the user currently using this app
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Access a shelter on display
     * @return a Shelter object which is currently focused on
     */
    public Shelter getShelter() {
        return shelter;
    }

    /**
     * Set the current focused shelter
     * @param shelter the current focused on shelter
     */
    public void setCurrentShelter(Shelter shelter) {
        this.shelter = shelter;
    }



    /**
     * The filter logic about gender requirement
     * @param gender the given gender requirement
     * @param constraint the constraint which may include the gender requirement
     * @return a boolean value which would be true if the requirement is matched
     */
    public boolean filterByGender(CharSequence gender, String constraint) {
        if ("Any".equals(gender)) {
            return true;
        } else return constraint.contains(gender);
    }

    /**
     * Check if the input user information exists in the database
     * @param database the database containing all user information
     * @param userId the input user id
     * @param password the input user password
     * @return a boolean value which would be true if the input information exists in database
     */
    public boolean loginUser(Map<String, User> database, String userId, String password) {
        if ((database.get(userId) != null) && database.get(userId).getPassword().equals(password)) {
            Model.getInstance().setUser(database.get(userId));
            return true;
        } else {
            return false;
        }
    }

    /**
     * The filter logic about age requirement
     * @param ageRange the given age requirement
     * @param constraint the constraint which may include the age requirement
     * @return a boolean value which would be true if the requirement is matched
     */
    public boolean filterByAge(String ageRange, String constraint) {
        if ("Anyone".equals(ageRange)) {
            return true;
        } else {
            return constraint.toLowerCase().contains(ageRange.toLowerCase())
                    || "Families with newborns".equals(ageRange)
                    && constraint.contains("Families w");
        }
    }

    /**
     * The filter logic of searching by shelter name
     * @param sheltername the input shelter name
     * @param currentShelter the current shelter existing in database
     * @return a boolean value which would be true if the given shelter is found
     */
    public boolean filterByName(String sheltername, Shelter currentShelter) {
        return currentShelter.getName().toLowerCase().contains(sheltername.toLowerCase());
    }

    /**
     * Add a new registered user to database
     * @param database the database which contains all user information
     * @param userId the input user id
     * @param password the input user password
     * @param confirm the input confirmed password
     * @param username the input email address
     * @return a boolean value which would be true if the mew user is successfully added/
     */
    public boolean registerUser(Map<String, User> database, String userId,
                                String password, String confirm,
                                String username) {
        if (password.equals(confirm) && !database.containsKey(userId)) {
            User curUser = new ShelterEmployee(username, userId, password, true, "1");
            database.put(userId, curUser);
            return true;
        }
        return false;
    }

            

    // database work
//    public void addNewShelter(String shelterId, String name, String capacity, String restriction,
//                              String longitude, String latitude, String address,
//                              String specialNotes, String phoneNum, int occupied) {
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
//
//        db.child("shelters").child(shelterId).child("name").setValue(name);
//        db.child("shelters").child(shelterId).child("capacity").setValue(capacity);
//        db.child("shelters").child(shelterId).child("restriction").setValue(restriction);
//        db.child("shelters").child(shelterId).child("longitude").setValue(longitude);
//        db.child("shelters").child(shelterId).child("latitude").setValue(latitude);
//        db.child("shelters").child(shelterId).child("address").setValue(address);
//        db.child("shelters").child(shelterId).child("specialNotes").setValue(specialNotes);
//        db.child("shelters").child(shelterId).child("phoneNum").setValue(phoneNum);
//        db.child("shelters").child(shelterId).child("occupied").setValue(occupied);
//
//    }


//    public void addNewShelterEmployee(String name, String userId, String password,
//                                      boolean accountState, String shelterId,
//                                      Shelter claim, int beds) {
//
//        DatabaseReference firebaseUsers
//                = FirebaseDatabase.getInstance().getReference().child("users");
//
//        firebaseUsers.child(userId).child("name").setValue(name);
//        firebaseUsers.child(userId).child("userId").setValue(userId);
//        firebaseUsers.child(userId).child("password").setValue(password);
//        firebaseUsers.child(userId).child("accountState").setValue(accountState);
//        firebaseUsers.child(userId).child("shelterId").setValue(shelterId);
//        firebaseUsers.child(userId).child("type").setValue("shelterEmployee");
//        if (claim != null) {
//            firebaseUsers.child(userId).child("claim").setValue(claim.getShelterId());
//        } else {
//            firebaseUsers.child(userId).child("claim").setValue("null");
//        }
//        firebaseUsers.child(userId).child("beds").setValue(beds);
//    }

//    public void addNewAdmin(String name, String userId, String password, boolean accountState,
//                            Shelter claim, int beds) {
//        DatabaseReference firebaseUsers
//                = FirebaseDatabase.getInstance().getReference().child("users");
//
//        firebaseUsers.child(userId).child("name").setValue(name);
//        firebaseUsers.child(userId).child("userId").setValue(userId);
//        firebaseUsers.child(userId).child("password").setValue(password);
//        firebaseUsers.child(userId).child("accountState").setValue(accountState);
//        firebaseUsers.child(userId).child("type").setValue("admin");
//        if (claim != null) {
//            firebaseUsers.child(userId).child("claim").setValue(claim.getShelterId());
//        } else {
//            firebaseUsers.child(userId).child("claim").setValue("null");
//        }
//        firebaseUsers.child(userId).child("beds").setValue(beds);
//    }

//    public void addNewHomeles(String name, String userId, String password,
//                              boolean accountState, String govId,
//                              String gender, boolean isVeteran, boolean isFamily,
//                              int familyNum, int age,
//                              Shelter claim, int beds) {
//        DatabaseReference firebaseUsers
//                = FirebaseDatabase.getInstance().getReference().child("users");
//
//        firebaseUsers.child(userId).child("name").setValue(name);
//        firebaseUsers.child(userId).child("userId").setValue(userId);
//        firebaseUsers.child(userId).child("password").setValue(password);
//        firebaseUsers.child(userId).child("accountState").setValue(accountState);
//        firebaseUsers.child(userId).child("govId").setValue(govId);
//        firebaseUsers.child(userId).child("gender").setValue(gender);
//        firebaseUsers.child(userId).child("isVeteran").setValue(isVeteran);
//        firebaseUsers.child(userId).child("isFamily").setValue(isFamily);
//        firebaseUsers.child(userId).child("familyNum").setValue(familyNum);
//        firebaseUsers.child(userId).child("age").setValue(age);
//        firebaseUsers.child(userId).child("type").setValue("homeless");
//        if (claim != null) {
//            firebaseUsers.child(userId).child("claim").setValue(claim.getShelterId());
//        } else {
//            firebaseUsers.child(userId).child("claim").setValue("null");
//        }
//        firebaseUsers.child(userId).child("beds").setValue(beds);
//
//    }


    @Override
    public String toString() {
        return "hello";
    }

    /**
     * Add shelters
     * @param shelterInfo the information of the added shelter
     */
    public void setShelters(Map<String, Shelter> shelterInfo) {
        shelters = new HashMap<>();
        for (String key: shelterInfo.keySet()) {
            shelters.put(key, shelterInfo.get(key));
        }
    }
}
