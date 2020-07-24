package com.example.shopsmart.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    @Exclude
    private String id;
    @Exclude
    private String username;
    @Exclude
    private String email;
    @Exclude
    private String password;
    private ArrayList<Product> cart;

    public User() {
        // Required
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.cart = new ArrayList<>();
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

    @Exclude
    public String getUsername() {
        return username;
    }

    @Exclude
    public void setUsername(String username) {
        this.username = username;
    }

    @Exclude
    public String getEmail() {
        return email;
    }

    @Exclude
    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    @Exclude
    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Product> getCart() { return cart; }

    public void setCart(ArrayList<Product> cart) { this.cart = cart; }

}
