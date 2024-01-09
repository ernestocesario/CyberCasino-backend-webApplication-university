package org.example.cybercasino.controller.Details.utils;

import org.example.cybercasino.model.DTOs.utils.Match;

public class SimpleMatch {
    private final String username;
    private final String game;
    private final double amount;

    private SimpleMatch(String username, String game, double amount) {
        this.username = username;
        this.game = game;
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public String getGame() {
        return game;
    }

    public double getAmount() {
        return amount;
    }


    public static SimpleMatch convertToSimpleMatch(Match match) {
        return new SimpleMatch(match.user().getUsername(), match.gameType().getCommonName(), match.amount());
    }
}
