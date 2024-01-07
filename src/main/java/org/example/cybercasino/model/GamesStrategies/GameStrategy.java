package org.example.cybercasino.model.GamesStrategies;

import org.example.cybercasino.utils.GeneratedGame;

import java.util.List;

public abstract class GameStrategy {
    public final GeneratedGame generate(double bet, Object ...args) {
        if (!checkArgs(args)) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        Object gameConstants = args[0];

        boolean isWin = willWin(gameConstants);
        List<String> gameResult = generateResult(gameConstants, isWin);
        double amount = calculateAmount(gameResult, bet, isWin, gameConstants);

        return new GeneratedGame(gameResult, isWin, amount);
    }

    protected abstract boolean checkArgs(Object ...args);

    protected abstract boolean willWin(Object gameConstants);

    protected abstract List<String> generateResult(Object gameConstants, boolean isWin);

    protected abstract double calculateAmount(List<String> gameResult, double bet, boolean isWin, Object gameConstants);
}
