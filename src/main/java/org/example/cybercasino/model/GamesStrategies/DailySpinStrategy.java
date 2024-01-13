package org.example.cybercasino.model.GamesStrategies;

import org.example.cybercasino.model.constants.Games.DailySpinConstants;

import java.util.List;
import java.util.Random;

public class DailySpinStrategy extends GameStrategy {
    private static DailySpinStrategy instance;
    Random random = new Random();
    private DailySpinStrategy() {
        super();
    }

    public static DailySpinStrategy getInstance() {
        if (instance == null) {
            instance = new DailySpinStrategy();
        }
        return instance;
    }

    @Override
    protected boolean checkArgs(List<Object> betOn, Object gameConstants) {
        return gameConstants instanceof DailySpinConstants;
    }

    @Override
    protected boolean willWin(Object gameConstants) {
        DailySpinConstants dailySpinConstants = (DailySpinConstants) gameConstants;
        return random.nextInt(100) < dailySpinConstants.winningProbability;
        //essendo la .winningProbability = 50, la funzione restituisce true con probabilità 50%,
        //quindi il giocatore vince con probabilità 50%
    }

    //responsabile di generare il risultato del gioco, in base alle costanti del daily spin
    @Override
    protected List<String> generateResult(List<Object> betOn, boolean isWin, Object gameConstants) {
        DailySpinConstants dailySpinConstants = (DailySpinConstants) gameConstants;

        // se la funzione willWin ha deciso che il giocatore deve vincere
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
    protected double calculateAmount(List<String> gameResult, double bet, List<Object> betOn, boolean isWin, Object gameConstants) {
        if (isWin)
            return Integer.parseInt(gameResult.get(0));
        return 0;
    }
}
