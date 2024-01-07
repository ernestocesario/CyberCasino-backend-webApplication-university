package org.example.cybercasino.controller.Games.utils;

import org.example.cybercasino.model.constants.Games.GameType;

public class GameInformation {
    private final String sessionToken;
    private final GameType gameType;
    private final int bet;
    private final String additionalInfo;

    public GameInformation(String sessionToken, GameType gameType, int bet, String additionalInfo) {
        this.sessionToken = sessionToken;
        this.gameType = gameType;
        this.bet = bet;
        this.additionalInfo = additionalInfo;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public GameType getGameType() {
        return gameType;
    }

    public int getBet() {
        return bet;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
