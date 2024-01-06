package org.example.cybercasino.model.DTOs;

import java.util.Date;

public class User {
    private final String username;
    private final String email;
    private final String hashedPassword;
    private double balance;
    private Date lastDailySpin;
    private boolean isBanned;

    public User(String email, String username, String hashedPassword, double balance, Date lastDailySpin, boolean isBanned) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.balance = balance;
        this.lastDailySpin = lastDailySpin;
        this.isBanned = isBanned;
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
        return balance;
    }

    public Date getLastDailySpin() {
        return lastDailySpin;
    }

    public boolean isDailySpinAvailable() {
        return lastDailySpin.before(new Date());
    }

    public boolean isBanned() {
        return isBanned;
    }


    //update balance
    public void addBalance(double amount) {
        balance += amount;
    }

    public void subtractBalance(double amount) {
        balance -= amount;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void updateLastDailySpin() {
        lastDailySpin = new Date();
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}