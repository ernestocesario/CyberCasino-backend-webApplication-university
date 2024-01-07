package org.example.cybercasino.model.constants.Games;

public class DailySpinConstants {
    private static DailySpinConstants instance;

    public static DailySpinConstants getInstance() {
        if (instance == null)
            instance = new DailySpinConstants();
        return instance;
    }

    public final int[] elements = {0, 1, 3, 5, 10, 200};
    public final int[] chances = {Integer.MAX_VALUE, 100, 8, 5, 3, 1};

    public final int loseElementIndex = 0;

    public final int winningProbability = 50;

    private DailySpinConstants() {
    }
}
