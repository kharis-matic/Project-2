package com.example.uliv.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Property {
    private String id, ownerId, title, street, purok, barangay, municipality, province;
    private String type; // "Dorm", "Boarding House", "Apartment"
    private String sexRestriction; // "Male", "Female", "Co-ed"
    private List<String> imageUrls;
    private int roomCount;
    private boolean availabilityStatus;

    public Property() {
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAddress() {
        return String.format("%s, %s, %s, %s, %s", this.street, this.purok, this.barangay, this.municipality, this.province);
    }

    /**
     * Adds one or more image URLs to the property.
     * This is especially useful when users upload images in the UI.
     * @param urls One or more image URLs to add
     */
    public void addImageUrl(String... urls) {
        if (this.imageUrls == null) {
            this.imageUrls = new ArrayList<>();
        }
        Collections.addAll(this.imageUrls, urls);
    }

    /**
     * Removes an image URL at the specified index.
     * Useful when a user deletes an uploaded image in the UI.
     * @param index The index of the image URL to remove
     * @return true if removal was successful, false otherwise
     */
    public boolean removeImageUrl(int index) {
        if (imageUrls != null && index >= 0 && index < imageUrls.size()) {
            imageUrls.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Removes a specific image URL.
     * Useful when a user deletes an uploaded image in the UI.
     * @param url The URL to remove
     * @return true if removal was successful, false otherwise
     */
    public boolean removeImageUrl(String url) {
        if (imageUrls != null) {
            return imageUrls.remove(url);
        }
        return false;
    }

    /**
     * Gets the primary image URL or null if no images exist.
     * @return The first image URL or null
     */
    public String getPrimaryImageUrl() {
        return imageUrls.isEmpty() ? null : imageUrls.get(0);
    }

    /**
     * Returns the list of image URLs associated with this property.
     * @return List of image URLs
     */
    public List<String> getImageUrls() {
        return imageUrls;
    }

}
