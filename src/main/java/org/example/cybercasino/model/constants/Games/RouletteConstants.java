package org.example.cybercasino.model.constants.Games;

public class RouletteConstants {
    private static RouletteConstants instance;

    public static RouletteConstants getInstance() {
        if (instance == null)
            instance = new RouletteConstants();
        return instance;
    }

    private RouletteConstants() {
    }
}
