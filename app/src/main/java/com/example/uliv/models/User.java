package com.example.uliv.models;

public class User {
    private int id;
    private String type; // "Renter" or "Owner"
    private String firstName;
    private String lastName;
    private String email;
    private String gender; // "Female" or "Male"
    private boolean activeStatus;
    private String phoneNumber;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public User() {
    }

    public User(String firstName, String lastName, String gender, String type, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.type = type;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.activeStatus = true;
    }


    public String getFullName() {
        return firstName + " " + lastName;
    }

}
