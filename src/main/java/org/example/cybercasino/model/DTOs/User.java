package org.example.cybercasino.model.DTOs;

import java.sql.Timestamp;

public class User {
    private String username;
    private String email;
    private String hashedPassword;
    private double Balance;
    private boolean dailySpinAvailable;
    private Timestamp creation_time;

    public User(String email, String username, String hashedPassword, double balance, boolean dailySpinAvailable, Timestamp creation_time) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        Balance = balance;
        this.dailySpinAvailable = dailySpinAvailable;
        this.creation_time = creation_time;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public double getBalance() {
        return Balance;
    }

    public boolean isDailySpinAvailable() {
        return dailySpinAvailable;
    }

    public Timestamp getCreation_time() {
        return creation_time;
    }
}
