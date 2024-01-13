package org.example.cybercasino.controller.Details.utils;

import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.DTOs.utils.Match;

public class Player {
    public String email;
    public String username;
    public double balance;
    public String lastDailySpin;
    public boolean isBanned;

    private Player(String email, String username, double balance, String lastDailySpin, boolean isBanned) {
        this.email = email;
        this.username = username;
        this.balance = balance;
        this.lastDailySpin = lastDailySpin;
        this.isBanned = isBanned;
    }

    public static Player convertToPlayer(User user) {
        return new Player(user.getEmail(), user.getUsername(), user.getBalance(), user.getLastDailySpin().toString(), user.isBanned());
    }
}
