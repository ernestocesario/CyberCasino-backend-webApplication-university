package org.example.cybercasino.model.GamesStrategies;

import java.util.List;

public class RouletteStrategy extends GameStrategy {
    private static RouletteStrategy instance;
    private RouletteStrategy() {
    }

    public static RouletteStrategy getInstance() {
        if (instance == null) {
            instance = new RouletteStrategy();
        }
        return instance;
    }

    @Override
    protected boolean checkArgs(Object... args) {
        return false;
    }

    @Override
    protected boolean willWin(Object gameConstants) {
        return false;
    }

    @Override
    protected List<String> generateResult(Object gameConstants, boolean isWin) {
        return null;
    }

    @Override
    protected double calculateAmount(double bet, boolean isWin, Object gameConstants) {
        return 0;
    }
}
