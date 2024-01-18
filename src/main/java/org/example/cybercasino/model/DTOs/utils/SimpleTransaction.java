package org.example.cybercasino.model.DTOs.utils;

public class SimpleTransaction {
    private final double amount;
    private final String transactionType;
    private final String timestamp;

    private SimpleTransaction(double amount, String transactionType, String timestamp) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public static SimpleTransaction convertToSimpleTransaction(Transaction transaction) {
        //beautify timestamp
        String timestamp = transaction.timestamp().toString().substring(0, transaction.timestamp().toString().lastIndexOf(":"));

        return new SimpleTransaction(transaction.amount(), transaction.transactionType().toString(), timestamp);
    }
}
