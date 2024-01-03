package org.example.cybercasino.model.DTOs;

import java.sql.Timestamp;

public class User {
    private String username;
    private String email;
    private String hashedPassword;
    private double Balance;
    private boolean dailySpinAvailable;

    public User(String email, String username, String hashedPassword, double balance, boolean dailySpinAvailable) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        Balance = balance;
        this.dailySpinAvailable = dailySpinAvailable;
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


    //update balance
    public void addBalance(double amount) {
        Balance += amount;
    }

    public void subtractBalance(double amount) {
        Balance -= amount;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }
}
