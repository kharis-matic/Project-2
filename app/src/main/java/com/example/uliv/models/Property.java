package com.example.uliv.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Represents a property listing in the Uliv application.
 * This class defines the structure for property data, including its details,
 * address, specifications, and associated rooms.
 */
public class Property {
    // --- Fields from your existing model ---
    private String id;
    private String ownerId; // Corresponds to ownerUid in PostAddActivity
    private String title;
    private String street;
    private String purok;
    private String barangay;
    private String municipality;
    private String province;
    private String type; // "Dorm", "Boarding House", "Apartment" - Corresponds to category in PostAddActivity
    private String sexRestriction;
    private List<String> imageUrls;
    private int roomCount; // This might be a summary. Detailed rooms are in the 'rooms' list.
    private boolean availabilityStatus;

    // --- Added/Updated Fields based on AdapterProperty and PostAddActivity needs ---
    private String description;
    private double price; // Overall property price or starting price for display in AdapterProperty
    private String date;  // Date string for display in AdapterProperty (e.g., "01/01/2025")
    private int totalBathrooms;
    private List<Map<String, Object>> rooms; // For detailed room information from PostAddActivity
    private long timestampCreated; // Timestamp of creation

    /**
     * Default constructor.
     * Required for Firebase Realtime Database/Firestore deserialization.
     */
    public Property() {
        // Initialize lists to prevent NullPointerExceptions
        this.imageUrls = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    /**
     * Parameterized constructor.
     * (Consider which fields are truly essential for initial construction vs. set later)
     */
    public Property(String id, String type, String ownerId, String title, String description,
                    String sexRestriction, int roomCount, boolean availabilityStatus,
                    String street, String purok, String barangay, String municipality, String province,
                    double price, String date, int totalBathrooms,
                    List<String> imageUrls, List<Map<String, Object>> rooms, long timestampCreated) {
        this.id = id;
        this.type = type;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.sexRestriction = sexRestriction;
        this.roomCount = roomCount;
        this.availabilityStatus = availabilityStatus;
        this.street = street;
        this.purok = purok;
        this.barangay = barangay;
        this.municipality = municipality;
        this.province = province;
        this.price = price;
        this.date = date;
        this.totalBathrooms = totalBathrooms;
        this.imageUrls = imageUrls != null ? imageUrls : new ArrayList<>();
        this.rooms = rooms != null ? rooms : new ArrayList<>();
        this.timestampCreated = timestampCreated;
    }

    // --- Getters and Setters for existing fields ---

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getPurok() {
        return purok;
    }
    public void setPurok(String purok) {
        this.purok = purok;
    }

    public String getBarangay() {
        return barangay;
    }
    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getMunicipality() {
        return municipality;
    }
    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }

    public String getType() { // Used as 'category' in AdapterProperty
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

    public List<String> getImageUrls() {
        if (this.imageUrls == null) {
            this.imageUrls = new ArrayList<>(); // Defensive initialization
        }
        return imageUrls;
    }
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getRoomCount() {
        return roomCount;
    }
    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public boolean isAvailabilityStatus() { // Getter for boolean
        return availabilityStatus;
    }
    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    // --- Getters and Setters for newly added/updated fields ---

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() { // Needed by AdapterProperty
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() { // Needed by AdapterProperty
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalBathrooms() {
        return totalBathrooms;
    }
    public void setTotalBathrooms(int totalBathrooms) {
        this.totalBathrooms = totalBathrooms;
    }

    public List<Map<String, Object>> getRooms() {
        if (this.rooms == null) {
            this.rooms = new ArrayList<>(); // Defensive initialization
        }
        return rooms;
    }
    public void setRooms(List<Map<String, Object>> rooms) {
        this.rooms = rooms;
    }

    public long getTimestampCreated() {
        return timestampCreated;
    }
    public void setTimestampCreated(long timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    // --- Helper Methods from your existing model ---

    /**
     * Returns a formatted address string.
     * @return Formatted address.
     */
    public String getAddress() {
        // Build the address string, handling potentially null or empty optional fields like street
        StringBuilder addressBuilder = new StringBuilder();
        if (street != null && !street.isEmpty()) {
            addressBuilder.append(street).append(", ");
        }
        if (purok != null && !purok.isEmpty()) {
            addressBuilder.append(purok).append(", ");
        }
        if (barangay != null && !barangay.isEmpty()) {
            addressBuilder.append(barangay).append(", ");
        }
        if (municipality != null && !municipality.isEmpty()) {
            addressBuilder.append(municipality).append(", ");
        }
        if (province != null && !province.isEmpty()) {
            addressBuilder.append(province);
        }
        // Trim trailing comma and space if present
        String fullAddress = addressBuilder.toString();
        if (fullAddress.endsWith(", ")) {
            fullAddress = fullAddress.substring(0, fullAddress.length() - 2);
        }
        return fullAddress;
    }

    /**
     * Adds one or more image URLs to the property.
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
     * Gets the primary image URL (the first one in the list) or null if no images exist.
     * @return The first image URL or null
     */
    public String getPrimaryImageUrl() {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return null; // Or a default placeholder URL string
        }
        return imageUrls.get(0);
    }
}
