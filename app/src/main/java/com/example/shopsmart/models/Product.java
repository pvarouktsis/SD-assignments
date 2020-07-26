package com.example.shopsmart.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Product implements Serializable {
    @Exclude
    private String id;
    private String name;
    private Double price;
    private String imageURL;

    public Product() {
        // Required
    }

    public Product(String name, Double price, String imageURL) {
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Product(String id, String name, Double price, String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageURL() { return imageURL; }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Exclude
    public String getPriceToString() { return Double.toString(price); }

    @Exclude
    public String getPriceToStringWithEuroSymbol() {
        return Double.toString(price) + " \u20ac"; // \u20ac is for euro sign
    }

}
