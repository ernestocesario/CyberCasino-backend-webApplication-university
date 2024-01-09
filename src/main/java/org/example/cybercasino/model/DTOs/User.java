package org.example.cybercasino.model.DTOs;

import java.sql.Date;

public class User {
    private final String username;
    private final String email;
    private final String hashedPassword;
    private double balance;
    private Date lastDailySpin;
    private boolean isBanned;

    private TransactionHistory transactionHistory;
    private GameHistory gameHistory;

    public User(String email, String username, String hashedPassword, double balance, Date lastDailySpin, boolean isBanned) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.balance = balance;
        this.lastDailySpin = lastDailySpin;
        this.isBanned = isBanned;
    }

    public final String getUsername() {
        return username;
    }

    public final String getEmail() {
        return email;
    }

    public final String getHashedPassword() {
        return hashedPassword;
    }

    public final double getBalance() {
        return balance;
    }

    public final Date getLastDailySpin() {
        return lastDailySpin;
    }

    public final boolean isBanned() {
        return isBanned;
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

    public GameHistory getGameHistory() {
        return gameHistory;
    }


    //update balance
    public final void addBalance(double amount) {
        balance += amount;
    }

    public final void subtractBalance(double amount) {
        balance -= amount;
    }

    public final void setBalance(double balance) {
        this.balance = balance;
    }

    public final void setLastDailySpin(Date lastDailySpin) {
        this.lastDailySpin = lastDailySpin;
    }

    public final void setBanned(boolean banned) {
        isBanned = banned;
    }
}