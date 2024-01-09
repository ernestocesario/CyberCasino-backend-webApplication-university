package org.example.cybercasino.model.GamesStrategies;

import org.example.cybercasino.model.constants.Games.RouletteConstants;
import org.example.cybercasino.model.constants.Games.SlotMachine.SlotMachineConstants;

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
        //NON SO CHE CONTROLLO DEVO FARE QUI !!!!!!!!!!!!!!!!!!!!!!!!!!
        if (args.length != 1) {
            return false;
        }
        return args[0] instanceof RouletteConstants;
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
