package com.example.uliv.models;


public class Room {
    private String id, propertyId;
    private boolean availabilityStatus;
    private int bedspaceCount;
    private double monthlyRentalFee;
    private String description;

    public Room() {
    }

    public Room(String id, String propertyId, boolean availabilityStatus, int bedspaceCount, double monthlyRentalFee, String description) {
        this.id = id;
        this.propertyId = propertyId;
        this.availabilityStatus = availabilityStatus;
        this.bedspaceCount = bedspaceCount;
        this.monthlyRentalFee = monthlyRentalFee;
        this.description = description;
    }

    public boolean isAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public int getBedspaceCount() {
        return bedspaceCount;
    }

    public void setBedspaceCount(int bedspaceCount) {
        this.bedspaceCount = bedspaceCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMonthlyRentalFee() {
        return monthlyRentalFee;
    }

    public void setMonthlyRentalFee(double monthlyRentalFee) {
        this.monthlyRentalFee = monthlyRentalFee;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
}
