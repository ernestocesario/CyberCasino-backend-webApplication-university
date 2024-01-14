package org.example.cybercasino.model.GamesStrategies;

import org.example.cybercasino.model.constants.Games.HorseRaceConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HorseRaceStrategy extends GameStrategy {
    private static HorseRaceStrategy instance = null;
    Random random = new Random();

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
        System.out.println("betOn: "+betOn.get(0));
        System.out.println("betOn: "+betOn.get(0).getClass());
        //qui devi controllare che betOn sia una lista di ciò che ti serve

        //in HorseRace betOn non sarà altro che un lista di stringhe con un solo valore, che contiene
        //il nome del cavallo su cui si vuole scommettere. es: betOn = ["horse1"];
        boolean isbetOnValid = betOn.size() == 1 && betOn.get(0) instanceof String;
        return gameConstants instanceof HorseRaceConstants && isbetOnValid;
    }

    @Override
    protected boolean willWin(List<Object> betOn, Object gameConstants) {
        HorseRaceConstants horseRaceConstants = (HorseRaceConstants) gameConstants;
        return random.nextInt(100) < horseRaceConstants.winningPercentage;
    }

    @Override
    protected List<String> generateResult(List<Object> betOn, boolean isWin, Object gameConstants) {
        HorseRaceConstants horseRaceConstants = (HorseRaceConstants) gameConstants;

        List<String> result = new ArrayList<>(); // nel mio caso conterrà solo un valore che sarà il nome del cavallo vincente
        if (isWin) {
            System.out.println("toString: "+betOn.get(0).toString());
            System.out.println("getClass: "+betOn.get(0).getClass());
            System.out.println("normal: "+betOn.get(0));

            result.add(betOn.get(0).toString());
            // se isWin è true, allora faccio uscire come cavallo vincente quello su cui si è scommesso
        } else {
            String winningHorse = horseRaceConstants.horses[random.nextInt(horseRaceConstants.horses.length)];
            while (winningHorse.equals(betOn.get(0))) {
                winningHorse = horseRaceConstants.horses[random.nextInt(horseRaceConstants.horses.length)];
            }
            result.add(winningHorse);
        }
        return result;
    }

    @Override
    protected double calculateAmount(List<String> gameResult, double bet, List<Object> betOn, boolean isWin, Object gameConstants) {
        HorseRaceConstants horseRaceConstants = (HorseRaceConstants) gameConstants;
        return isWin ? bet * horseRaceConstants.betMultiplier : bet;
    }
}
