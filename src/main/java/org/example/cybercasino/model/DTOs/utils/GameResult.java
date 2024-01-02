package org.example.cybercasino.model.DTOs.utils;

import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.DTOs.Game;

import java.sql.Timestamp;

public class GameResult {
    private String id;
    private User user;
    private Game game;
    private double amount;
    private Timestamp timestamp;
}
