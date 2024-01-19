package org.example.cybercasino.model.constants.Games;

public class GuessTheCardConstants {
    public final int numberOfCards = 4;
    public final int winningPercentage = 50;
    public final int betMultiplier = 4;
    private static GuessTheCardConstants instance;

    public static GuessTheCardConstants getInstance() {
        if (instance == null)
            instance = new GuessTheCardConstants();
        return instance;
    }

    private GuessTheCardConstants() {
    }
}
