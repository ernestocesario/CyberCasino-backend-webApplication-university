package org.example.cybercasino.model.constants.Games;

public class HorseRaceConstants {
    public final int numberOfHorses = 4; //forse non mi serve
    public final String[] horses = {
            "horse1",
            "horse2",
            "horse3",
            "horse4"
    };
    public final int winningPercentage = 10;
    public final int betMultiplier = 4;
    private static HorseRaceConstants instance;

    public static HorseRaceConstants getInstance() {
        if (instance == null)
            instance = new HorseRaceConstants();
        return instance;
    }

    private HorseRaceConstants() {
    }

}
