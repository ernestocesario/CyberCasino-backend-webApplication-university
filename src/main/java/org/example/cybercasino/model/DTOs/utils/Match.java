package org.example.cybercasino.model.DTOs.utils;

import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.GameType;

import java.sql.Timestamp;

public record Match(User user, GameType gameType, double amount, MatchResult matchResult, Timestamp timestamp) {
}
