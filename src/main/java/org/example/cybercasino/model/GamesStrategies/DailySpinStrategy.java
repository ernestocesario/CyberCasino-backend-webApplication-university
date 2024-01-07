package org.example.cybercasino.model.GamesStrategies;

import org.example.cybercasino.model.constants.Games.DailySpinConstants;

import java.util.List;
import java.util.Random;

public class DailySpinStrategy extends GameStrategy {
    private static DailySpinStrategy instance;
    Random random = new Random();
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
        if (args.length != 1) {
            return false;
        }
        return args[0] instanceof DailySpinConstants;
    }

    @Override
    protected boolean willWin(Object gameConstants) {
        DailySpinConstants dailySpinConstants = (DailySpinConstants) gameConstants;
        return random.nextInt(100) < dailySpinConstants.winningProbability;
    }

    @Override
    protected List<String> generateResult(Object gameConstants, boolean isWin) {
        DailySpinConstants dailySpinConstants = (DailySpinConstants) gameConstants;

        if (isWin) {
            int winType = random.nextInt(100);

            int minChanceFound = Integer.MAX_VALUE;
            int winningElementPos = 0;
            for(int i = 0; i < dailySpinConstants.chances.length; i++) {
                if(dailySpinConstants.chances[i] < minChanceFound && winType < dailySpinConstants.chances[i]) {
                    winningElementPos = i;
                    minChanceFound = dailySpinConstants.chances[i];
                }
            }

            return List.of(String.valueOf(dailySpinConstants.elements[winningElementPos]));
        }
        return List.of(String.valueOf(dailySpinConstants.elements[dailySpinConstants.loseElementIndex]));
    }

    @Override
    protected double calculateAmount(List<String> gameResult, double bet, boolean isWin, Object gameConstants) {
        if (isWin)
            return Integer.parseInt(gameResult.get(0));
        return 0;
    }
}
