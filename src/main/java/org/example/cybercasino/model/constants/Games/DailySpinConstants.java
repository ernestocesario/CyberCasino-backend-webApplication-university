package org.example.cybercasino.model.constants.Games;

public class DailySpinConstants {
    private static DailySpinConstants instance;

    public static DailySpinConstants getInstance() {
        if (instance == null)
            instance = new DailySpinConstants();
        return instance;
    }

    private DailySpinConstants() {
    }
}
