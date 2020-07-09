package com.example.shopsmart.classes;

// import java.net.URL;

public class Product {
    private String productID;
    private String productImageURL;
    private String productName;
    private double productPrice;

    public Product() {
        // Required
    }

    public Product(String productID,
                   String productImageURL,
                   String productName,
                   double productPrice) {
        this.productID = productID;
        this.productImageURL = productImageURL;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
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
}
