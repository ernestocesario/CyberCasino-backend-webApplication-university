package org.example.cybercasino.model.GamesStrategies;

import java.util.List;

public class DailySpinStrategy extends GameStrategy {
    private static DailySpinStrategy instance;
    private DailySpinStrategy() {
    }

    public static DailySpinStrategy getInstance() {
        if (instance == null) {
            instance = new DailySpinStrategy();
        }
        return instance;
    }

    @Override
    public List<String> generate(Object ...args) {
        return null;
    }

    @Override
    public boolean isWinning(List<String> result) {
        return false;
    }
}
