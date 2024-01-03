package org.example.cybercasino.controller.Games.utils;

public class GameInformation {
    private final String sessionToken;
    private final String gameName;
    private final int bet;

    public GameInformation(String sessionToken, String gameName, int bet) {
        this.sessionToken = sessionToken;
        this.gameName = gameName;
        this.bet = bet;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getGameName() {
        return gameName;
    }

    public int getBet() {
        return bet;
    }
}
