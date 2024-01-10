package org.example.cybercasino.controller.Details.utils;

import org.example.cybercasino.model.DTOs.utils.Transaction;

public class SimpleTransaction {
    private final long id;
    private final String username;
    private final double amount;
    private final String transactionType;
    private final String timestamp;

    private SimpleTransaction(long id, String username, double amount, String transactionType, String timestamp) {
        this.id = id;
        this.username = username;
        this.amount = amount;
        this.transactionType = transactionType;
        this.timestamp = timestamp;
    }

    public static SimpleTransaction convertToSimpleTransaction(Transaction transaction) {
        return new SimpleTransaction(transaction.id(), transaction.user().getUsername(), transaction.amount(), transaction.transactionType().toString(), transaction.timestamp().toString());
    }
}
