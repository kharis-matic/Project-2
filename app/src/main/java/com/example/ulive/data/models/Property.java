package com.example.ulive.data.models;

public class Property {
    private String id;
    private String ownerId;
    private String type; // "Dorm", "Boarding House", "Apartment"
    private String sexRestriction; // "Male", "Female", "Co-ed"
    private String street;
    private String purok;
    private String barangay;
    private String municipality;
    private String province;
    private int roomCount;
    private boolean availabilityStatus;

    public Property(String id, String type, String ownerId, String sexRestriction, int roomCount, String street, String purok, String barangay, String municipality, String province) {
        this.id = id;
        this.type = type;
        this.ownerId = ownerId;
        this.sexRestriction = sexRestriction;
        this.roomCount = roomCount;
        this.street = street;
        this.purok = purok;
        this.barangay = barangay;
        this.municipality = municipality;
        this.province = province;
        this.availabilityStatus = true;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getPurok() {
        return purok;
    }

    public void setPurok(String purok) {
        this.purok = purok;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSexRestriction() {
        return sexRestriction;
    }

    public void setSexRestriction(String sexRestriction) {
        this.sexRestriction = sexRestriction;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }
}
