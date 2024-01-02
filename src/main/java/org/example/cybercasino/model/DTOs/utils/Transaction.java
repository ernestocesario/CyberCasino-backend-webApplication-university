package org.example.cybercasino.model.DTOs.utils;

import org.example.cybercasino.model.DTOs.User;

import java.sql.Timestamp;

public class Transaction {
    private String id;
    private User user;
    private double amount;
    private TransactionType transactionType;
    private Timestamp timestamp;
}
