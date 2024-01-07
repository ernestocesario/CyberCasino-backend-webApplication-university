package org.example.cybercasino.model.GamesStrategies;

import java.util.List;

public class DailySpinStrategy extends GameStrategy {
    private static DailySpinStrategy instance;
    private DailySpinStrategy() {
    }

    public static DailySpinStrategy getInstance() {
        if (instance == null) {
            instance = new DailySpinStrategy();
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
