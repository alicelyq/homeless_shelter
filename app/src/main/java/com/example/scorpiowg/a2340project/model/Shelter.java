package com.example.scorpiowg.a2340project.model;

import android.util.Log;

/**
 * This is Homeless Shelter object.
 * @author Jingbo Wang
 * @version 1.8
 * created on 02/25/2018
 */

public class Shelter {
    private String shelterId;
    private String name;
    private String capacity;
    private String restriction;
    private String longitude;
    private String latitude;
    private String address;
    private String specialNotes;
    private String phoneNum;
    private int occupied;

    /**
     * Constructor used for db.
     */
    public Shelter() {
    }
    /**
     * Construct a Shelter object with given information
     * @param shelterId the id of this shelter
     * @param name the name of this shelter
     * @param capacity the initial capacity of this shelter
     * @param restriction the shelter restriction on types of homeless
     * @param longitude the longitude of the location of this shelter
     * @param latitude the latitude of the location of this shelter
     * @param address the address of this shelter
     * @param specialNotes special notes from the shelter owner
     * @param phoneNum contact number of this shelter
     */
    public Shelter(String shelterId, String name, String capacity, String restriction, String longitude, String latitude, String address, String specialNotes, String phoneNum) {
        this.shelterId = shelterId;
        this.name = name;
        this.capacity = capacity;
        this.restriction = restriction;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.specialNotes = specialNotes;
        this.phoneNum = phoneNum;
        this.occupied = 0;
    }
    /**
     * Getter of the shelter's id
     * @return a String which is the id of this shelter
     */
    public String getShelterId() {
        return shelterId;
    }

    /**
     * Getter of the shelter's name
     * @return a String which is the name of this shelter
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of the shelter's capacity
     * @return a String which is the capacity of this shelter
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * Getter of the shelter's restriction
     * @return a String which is the restriction of this shelter
     */
    public String getRestriction() {
        return restriction;
    }

    /**
     * Getter of the shelter's longitude
     * @return a String which is the longitude of this shelter
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Getter of the shelter's latitude
     * @return a String which is the latitude of this shelter
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Getter of the shelter's address
     * @return a String which is the address of this shelter
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter of the shelter's contact number
     * @return a String which is the contact number of this shelter
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * Getter of the shelter's special notes
     * @return a String which is the special notes of this shelter
     */
    public String getSpecialNotes() {
        return specialNotes;
    }

    /**
     * Getter of the shelter's occupied bedspace
     * @return a int which is the number of occupied bedspace in this shelter
     */
    public int getOccupied() {
        return occupied;
    }
    /**
     * Setter of the shelter's restriction
     * @param newOccupied which is the updated number of occupied bedspace
     */
    public void setOccupied(int newOccupied) {
        this.occupied = newOccupied;
    }

    /**
     * Return all information of the shelter
     * @return a String which contains all details of the shelter
     */
    public String toString() {
//        Log.d("nancytestparseint","test: " + getCapacity());
        return "SHELTER\nname: " + getName() + "\nID: " + getShelterId() + "\nCapacity: " + getCapacity() +
                "\nOccupied: " + getOccupied() + "\nAvailable: " + (Integer.parseInt(getCapacity()) - getOccupied()) +
                "\nRestriction: " + getRestriction() + "\nLongitude: " + getLongitude() + "\nLatitude: " + getLatitude() +
                "\nAddress: " + getAddress() + "\nPhone Number: " + getPhoneNum() + "\nSpecial Notes: " + getSpecialNotes();
    }
}
