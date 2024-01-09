package org.example.cybercasino.model.proxies;

import org.example.cybercasino.model.DTOs.GameHistory;
import org.example.cybercasino.model.DTOs.TransactionHistory;
import org.example.cybercasino.model.DTOs.User;

import java.sql.Date;

public class UserProxy extends User {
    public UserProxy(String email, String username, String hashedPassword, double balance, Date lastDailySpin, boolean isBanned) {
        super(email, username, hashedPassword, balance, lastDailySpin, isBanned);
    }

    @Override
    public TransactionHistory getTransactionHistory() {
        return super.getTransactionHistory();
    }

    @Override
    public GameHistory getGameHistory() {
        return super.getGameHistory();
    }
}
