package org.example.cybercasino.model.GamesStrategies;

import org.example.cybercasino.model.constants.Games.GuessTheCardConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuessTheCardStrategy extends GameStrategy {
    private static GuessTheCardStrategy instance = null;
    Random random = new Random();

    private GuessTheCardStrategy() {
        super();
    }

    public static GuessTheCardStrategy getInstance() {
        if (instance == null) {
            instance = new GuessTheCardStrategy();
        }
        return instance;
    }

    @Override
    protected boolean checkArgs(List<Object> betOn, Object gameConstants) {
        System.out.println("betOn: "+betOn.get(0));
        System.out.println("betOn: "+betOn.get(0).getClass());
        //qui devi controllare che betOn sia una lista di ciò che ti serve

        //in GuessTheCard betOn non sarà altro che un lista di interi con un solo valore, che contiene
        //l'indice su cui si vuole scommettere. es: betOn = [1];
        boolean isbetOnValid = betOn.size() == 1 && betOn.get(0) instanceof Integer;
        return gameConstants instanceof GuessTheCardConstants && isbetOnValid;
    }

    @Override
    protected boolean willWin(List<Object> betOn, Object gameConstants) {
        GuessTheCardConstants guessTheCardConstants = (GuessTheCardConstants) gameConstants;
        return random.nextInt(100) < guessTheCardConstants.winningPercentage;
    }

    @Override
    protected List<String> generateResult(List<Object> betOn, boolean isWin, Object gameConstants) {
        GuessTheCardConstants guessTheCardConstants = (GuessTheCardConstants) gameConstants;

        List<String> result = new ArrayList<>();
        if (isWin) {
            System.out.println("toString: "+betOn.get(0).toString());
            System.out.println("getClass: "+betOn.get(0).getClass());
            System.out.println("normal: "+betOn.get(0));

            result.add(betOn.get(0).toString());
        } else {
            Integer winningCard = random.nextInt(guessTheCardConstants.numberOfCards) + 1;
            while (winningCard.equals(betOn.get(0))) {
                winningCard = random.nextInt(guessTheCardConstants.numberOfCards) + 1;
            }
            result.add(winningCard.toString());
        }
        return result;
    }

    @Override
    protected double calculateAmount(List<String> gameResult, double bet, List<Object> betOn, boolean isWin, Object gameConstants) {
        GuessTheCardConstants guessTheCardConstants = (GuessTheCardConstants) gameConstants;
        return isWin ? bet * guessTheCardConstants.betMultiplier : bet;
    }
}
