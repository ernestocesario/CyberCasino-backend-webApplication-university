package org.example.cybercasino.controller.Details.utils;

import org.example.cybercasino.model.DTOs.utils.Transaction;

public class SimpleTransaction {
    public final String username;
    public final double amount;
    public final String transactionType;
    public final String timestamp;

    private SimpleTransaction(String username, double amount, String transactionType, String timestamp) {
        this.username = username;
        this.amount = amount;
        this.transactionType = transactionType;
        this.timestamp = timestamp;
    }

    public static SimpleTransaction convertToSimpleTransaction(Transaction transaction) {
        return new SimpleTransaction(transaction.user().getUsername(), transaction.amount(), transaction.transactionType().toString(), transaction.timestamp().toString());
    }
}
