package com.example.uliv.models;

public class Enquiry {
    private String id, renterId, propertyId;
    private String status; // "PENDING" or "ACCEPTED" or "REJECTED"

    public Enquiry(String id, String renterId, String roomId, String status) {
        this.id = id;
        this.renterId = renterId;
        this.propertyId = roomId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRenterId() {
        return renterId;
    }

    public void setRenterId(String renterId) {
        this.renterId = renterId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

