package org.example.cybercasino.model.GamesStrategies;

import java.util.List;

public class HorseRaceStrategy extends GameStrategy {
    private static HorseRaceStrategy instance = null;

    private HorseRaceStrategy() {
        super();
    }

    public static HorseRaceStrategy getInstance() {
        if (instance == null) {
            instance = new HorseRaceStrategy();
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
    protected double calculateAmount(List<String> gameResult, double bet, boolean isWin, Object gameConstants) {
        return 0;
    }
}
