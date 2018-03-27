package com.example.scorpiowg.a2340project.model;

/**
 * Created by wangjingbo on 2/25/18.
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

    public Shelter() {
        //used for db
    }

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

    public String getShelterId() {
        return shelterId;
    }

    public String getName() {
        return name;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getRestriction() {
        return restriction;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int newOccupied) {
        this.occupied = newOccupied;
    }

    public String toString() {
        return "SHELTER\nname: " + getName() + "\nID: " + getShelterId() + "\nCapacity: " + getCapacity() +
                "\nOccupied: " + getOccupied() + "\nAvailable: " + (Integer.parseInt(getCapacity()) - getOccupied()) +
                "\nRestriction: " + getRestriction() + "\nLongitude: " + getLongitude() + "\nLatitude: " + getLatitude() +
                "\nAddress: " + getAddress() + "\nPhone Number: " + getPhoneNum() + "\nSpecial Notes: " + getSpecialNotes();
    }
}
