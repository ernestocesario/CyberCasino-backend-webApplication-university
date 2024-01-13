package org.example.cybercasino.model.GamesStrategies;

import org.example.cybercasino.model.constants.Games.RouletteConstants;

import java.util.List;

public class RouletteStrategy extends GameStrategy {
    private static RouletteStrategy instance;
    private RouletteStrategy() {
        super();
    }

    public static RouletteStrategy getInstance() {
        if (instance == null) {
            instance = new RouletteStrategy();
        }
        return instance;
    }

    @Override
    protected boolean checkArgs(List<Object> betOn, Object gameConstants) {
        //qui devi controllare che betOn sia una lista di ciò che ti serve

        //se non usi costanti puoi togliere la riga sotto
        return gameConstants instanceof RouletteConstants;
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
