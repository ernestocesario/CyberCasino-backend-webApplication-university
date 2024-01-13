package org.example.cybercasino.model.GamesStrategies;

import org.example.cybercasino.model.constants.MessageConstants;
import org.example.cybercasino.utils.GeneratedGame;

import java.util.List;

public abstract class GameStrategy {
    public final GeneratedGame generate(double bet, List<Object> betOn, Object gameConstants) {
        if (!checkArgs(betOn, gameConstants)) {
            throw new IllegalArgumentException(MessageConstants.INVALID_ARGUMENTS.name());
        }

        //determino se il giocatore vincerà o meno
        boolean isWin = willWin(gameConstants);
        //genero il risultato del gioco
        List<String> gameResult = generateResult(betOn, isWin, gameConstants);
        //calcolo importo vinto o perso
        double amount = calculateAmount(gameResult, bet, betOn, isWin, gameConstants);
        //creazione e restituzione oggetto GeneratedGame contenente il risultato del gioco, se il giocatore ha vinto e l'importo vinto/perso
        return new GeneratedGame(gameResult, isWin, amount);
    }

    protected abstract boolean checkArgs(List<Object> betOn, Object gameConstants);

    protected abstract boolean willWin(Object gameConstants);

    protected abstract List<String> generateResult(List<Object> betOn, boolean isWin, Object gameConstants);

    protected abstract double calculateAmount(List<String> gameResult, double bet, List<Object> betOn, boolean isWin, Object gameConstants);
}
