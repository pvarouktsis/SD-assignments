package com.example.shopsmart.classes;

public class Product {
    private String productID;
    private String productName;
    private double productPrice;
    private String productImageURL;

    public Product() {
        // Required
    }

    public Product(String productID,
                   String productName,
                   double productPrice,
                   String productImageURL) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageURL = productImageURL;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }

}
