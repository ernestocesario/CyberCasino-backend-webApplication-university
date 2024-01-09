package org.example.cybercasino.model.constants.Games;

import org.example.cybercasino.model.GamesStrategies.*;
import org.example.cybercasino.model.constants.DatabaseConstants;
import org.example.cybercasino.model.constants.Games.DailySpinConstants;
import org.example.cybercasino.model.constants.Games.RouletteConstants;
import org.example.cybercasino.model.constants.Games.SlotMachine.FruitSlotMachineConstants;
import org.example.cybercasino.model.constants.Games.SlotMachine.MineSlotMachineConstants;
import org.example.cybercasino.model.constants.Games.SlotMachine.PremiumSlotMachineConstants;
import org.example.cybercasino.model.constants.Games.SlotMachine.SlotMachineType;

public enum GameType {
    SLOT_MACHINE,
    ROULETTE,
    DAILY_SPIN,
    HORSE_RACE;


    public GameStrategy getGameStrategy() {
        return switch (this) {
            case SLOT_MACHINE -> SlotStrategy.getInstance();
            case ROULETTE -> RouletteStrategy.getInstance();
            case DAILY_SPIN -> DailySpinStrategy.getInstance();
            case HORSE_RACE -> HorseRaceStrategy.getInstance();
        };
    }

    public Object getGameConstants() {
        return switch (this) {
            case SLOT_MACHINE -> throw new RuntimeException("No slot machine type specified");
            case ROULETTE -> RouletteConstants.getInstance();
            case DAILY_SPIN -> DailySpinConstants.getInstance();
            case HORSE_RACE -> HorseRaceConstants.getInstance();
        };
    }

    public Object getGameConstants(SlotMachineType slotMachineType) {
        if (this != SLOT_MACHINE) {
            throw new RuntimeException("This game type is not a slot machine");
        }

        return switch (slotMachineType) {
            case FRUIT -> FruitSlotMachineConstants.getInstance();
            case MINE -> MineSlotMachineConstants.getInstance();
            case PREMIUM -> PremiumSlotMachineConstants.getInstance();
        };
    }

    public String getCommonName() {
        String[] words = this.name().split("_");
        StringBuilder commonName = new StringBuilder();
        for (String word : words) {
            commonName.append(word.charAt(0)).append(word.substring(1).toLowerCase()).append(" ");
        }
        return commonName.toString().trim();
    }
}