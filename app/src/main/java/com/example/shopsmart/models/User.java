package com.example.shopsmart.models;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class User {
    @Exclude
    private String id;
    private String username;
    private String email;
    private String password;
    private ArrayList<Product> cart;

    public User() {
        // Required
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.cart = new ArrayList<>();
    }

    public User(String id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.cart = new ArrayList<>();
    }

    @Exclude
    public String getId() { return id; }

    @Exclude
    public void setId(String id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Product> getCart() { return cart; }

    public void setCart(ArrayList<Product> cart) { this.cart = cart; }

}
