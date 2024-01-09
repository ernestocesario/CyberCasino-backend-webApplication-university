package org.example.cybercasino.model.constants.Games;

public class HorseRaceConstants {
    private static HorseRaceConstants instance;

    public static HorseRaceConstants getInstance() {
        if (instance == null)
            instance = new HorseRaceConstants();
        return instance;
    }

    private HorseRaceConstants() {
    }
}
