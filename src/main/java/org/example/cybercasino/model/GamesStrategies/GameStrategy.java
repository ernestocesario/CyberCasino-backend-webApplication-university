package org.example.cybercasino.model.GamesStrategies;

import org.example.cybercasino.model.constants.MessageConstants;
import org.example.cybercasino.utils.GeneratedGame;

import java.util.List;

public abstract class GameStrategy {
    public final GeneratedGame generate(double bet, Object ...args) {
        if (!checkArgs(args)) {
            throw new IllegalArgumentException(MessageConstants.INVALID_ARGUMENTS.name());
        }
        Object gameConstants = args[0];

        //determino se il giocatore vincerà o meno
        boolean isWin = willWin(gameConstants);
        //genero il risultato del gioco
        List<String> gameResult = generateResult(gameConstants, isWin);
        //calcolo importo vinto o perso
        double amount = calculateAmount(gameResult, bet, isWin, gameConstants);
        //creazione e restituzione oggetto GeneratedGame contenente il risultato del gioco, se il giocatore ha vinto e l'importo vinto/perso
        return new GeneratedGame(gameResult, isWin, amount);
    }

    protected abstract boolean checkArgs(Object ...args);

    protected abstract boolean willWin(Object gameConstants);

    protected abstract List<String> generateResult(Object gameConstants, boolean isWin);

    protected abstract double calculateAmount(List<String> gameResult, double bet, boolean isWin, Object gameConstants);
}
