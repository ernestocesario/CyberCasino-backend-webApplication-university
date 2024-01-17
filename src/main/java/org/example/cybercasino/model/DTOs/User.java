package org.example.cybercasino.model.DTOs;

import org.example.cybercasino.model.DTOs.utils.Match;
import org.example.cybercasino.model.DTOs.utils.Transaction;
import org.example.cybercasino.model.constants.MessageConstants;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private final String email;
    private final String hashedPassword;
    private double balance;
    private Date lastDailySpin;
    private boolean isBanned;


    protected List<Transaction> transactionHistory;
    protected List<Match> winningGameHistory;

    public User(String email, String username, String hashedPassword, double balance, Date lastDailySpin, boolean isBanned) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.balance = balance;
        this.lastDailySpin = lastDailySpin;
        this.isBanned = isBanned;
        transactionHistory = new ArrayList<>();
        winningGameHistory = new ArrayList<>();
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

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public List<Match> getWinningGameHistory() {
        return winningGameHistory;
    }


    //update balance
    public final void addBalance(double amount) {
        balance += amount;
    }

    public final void subtractBalance(double amount) {
        if (amount > balance)
            throw new IllegalArgumentException(MessageConstants.USER_BALANCE_INSUFFICIENT.name());
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

    public final void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public final void setWinningGameHistory(List<Match> winningGameHistory) {
        this.winningGameHistory = winningGameHistory;
    }
}