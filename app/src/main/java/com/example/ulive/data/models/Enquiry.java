package com.example.ulive.data.models;

public class Enquiry {
    private String id;
    private String renterId;
    private String roomId;
    private String status; // "PENDING" or "ACCEPTED" or "REJECTED"

    public Enquiry(String id, String renterId, String roomId, String status) {
        this.id = id;
        this.renterId = renterId;
        this.roomId = roomId;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
