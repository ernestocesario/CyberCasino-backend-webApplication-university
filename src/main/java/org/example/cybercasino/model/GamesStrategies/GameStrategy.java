package org.example.cybercasino.model.GamesStrategies;

import java.util.List;

public abstract class GameStrategy {
    public abstract List<String> generate(Object ...args);
    
    public abstract boolean isWinning(List<String> result);
}
