package org.example.cybercasino.model.GamesStrategies;

import org.example.cybercasino.model.constants.Games.HorseRaceConstants;

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
    protected boolean checkArgs(List<Object> betOn, Object gameConstants) {
        //qui devi controllare che betOn sia una lista di ciò che ti serve

        return gameConstants instanceof HorseRaceConstants;
    }

    @Override
    protected boolean willWin(Object gameConstants) {
        return false;
    }

    @Override
    protected List<String> generateResult(List<Object> betOn, boolean isWin, Object gameConstants) {
        return null;
    }

    @Override
    protected double calculateAmount(List<String> gameResult, double bet, List<Object> betOn, boolean isWin, Object gameConstants) {
        return 0;
    }
}
