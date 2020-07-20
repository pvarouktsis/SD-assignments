package com.example.shopsmart.model;

public class Product {
    private String id;
    private String name;
    private Double price;
    private String imageURL;

    public Product() {
        // Required
    }

    public Product(String id,
                   String name,
                   Double price,
                   String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

    public String getPriceToString() {
        return Double.toString(price) + " \u20ac"; // \u20ac is for euro sign
    }

    public String getId() {
        return id;
    }

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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
