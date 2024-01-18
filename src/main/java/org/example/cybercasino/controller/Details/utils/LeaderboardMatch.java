package org.example.cybercasino.controller.Details.utils;

import org.example.cybercasino.model.DTOs.utils.Match;

public class LeaderboardMatch {
    private final String username;
    private final String gameType;
    private final double amount;

    private LeaderboardMatch(String username, String gameType, double amount) {
        this.username = username;
        this.gameType = gameType;
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public String getGameType() {
        return gameType;
    }

    public double getAmount() {
        return amount;
    }

    public static LeaderboardMatch convertToLeaderboardMatch(Match match) {
        return new LeaderboardMatch(match.user().getUsername(), match.gameType().getCommonName(), match.amount());
    }
}
