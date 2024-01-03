package org.example.cybercasino.model.GamesStrategies;

import java.util.List;

public class RouletteStrategy extends GameStrategy {
    private static RouletteStrategy instance;
    private RouletteStrategy() {
    }

    public static RouletteStrategy getInstance() {
        if (instance == null) {
            instance = new RouletteStrategy();
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
