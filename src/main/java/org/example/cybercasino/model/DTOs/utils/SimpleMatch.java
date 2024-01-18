package org.example.cybercasino.model.DTOs.utils;

public class SimpleMatch {
    private final String game;
    private final double amount;
    private final String timestamp;

    private SimpleMatch(String game, double amount, String timestamp) {
        this.game = game;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getGame() {
        return game;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }


    public static SimpleMatch convertToSimpleMatch(Match match) {
        //beautify timestamp
        String timestamp = match.timestamp().toString().substring(0, match.timestamp().toString().lastIndexOf(":"));

        return new SimpleMatch(match.gameType().getCommonName(), match.amount(), timestamp);
    }
}
