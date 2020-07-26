package com.example.shopsmart.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Product implements Serializable {
    @Exclude
    private String id;
    private String name;
    private Long price;
    private String imageURL;

    public Product() {
        // Required
    }

    public Product(String name, Long price, String imageURL) {
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Product(String id, String name, Long price, String imageURL) {
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

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Exclude
    public String getPriceToString() {
        String price = this.price.toString();
        String integer = price.substring(0, price.length() - 2);
        String decimal = price.substring(price.length() - 2);
        return integer + "." + decimal;
    }

    @Exclude
    public String getPriceToStringWithEuroSymbol() {
        String price = this.price.toString();
        String integer = price.substring(0, price.length() - 2);
        String decimal = price.substring(price.length() - 2);
        return integer + "." + decimal + " \u20ac"; // \u20ac is for euro sign
    }

}
