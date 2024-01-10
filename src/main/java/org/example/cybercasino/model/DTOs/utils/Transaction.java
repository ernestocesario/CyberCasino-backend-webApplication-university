package org.example.cybercasino.model.DTOs.utils;

import org.example.cybercasino.model.DTOs.User;

import java.sql.Timestamp;

public record Transaction(long id, User user, double amount, TransactionType transactionType, Timestamp timestamp) {
}
