package com.example.uliv.models;

public class ModelProperty {
    private String id, title, address, category, date, imageUrl;
    private double price;

    // Default constructor (required for Firebase)
    public ModelProperty() {}

    // Parameterized constructor
    public ModelProperty(String id, String title, String address, String category, String date,
                         double price, String imageUrl) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.category = category;
        this.date = date;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAddress() { return address; }
    public String getCategory() { return category; }
    public String getDate() { return date; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
}