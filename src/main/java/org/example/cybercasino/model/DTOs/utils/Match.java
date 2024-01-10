package org.example.cybercasino.model.DTOs.utils;

import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.constants.Games.GameType;

import java.sql.Timestamp;

public record Match(long id, User user, GameType gameType, double amount, Timestamp timestamp) {
}
