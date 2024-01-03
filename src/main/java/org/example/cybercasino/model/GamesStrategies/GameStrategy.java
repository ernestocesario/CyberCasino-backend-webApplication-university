package org.example.cybercasino.model.GamesStrategies;

import java.util.List;

public abstract class GameStrategy {
    public abstract List<Integer> generate(Object ...args);
    
    public abstract boolean isWinning(List<Integer> result);
}
