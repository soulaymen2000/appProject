package com.example.appprojet.models;

public class User {
    public int id;
    public String username;
    public String password;
    public String role; // "user" or "admin"

    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
