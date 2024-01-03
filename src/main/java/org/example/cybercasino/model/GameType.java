package org.example.cybercasino.model;

import org.example.cybercasino.model.GamesStrategies.DailySpinStrategy;
import org.example.cybercasino.model.GamesStrategies.GameStrategy;
import org.example.cybercasino.model.GamesStrategies.RouletteStrategy;
import org.example.cybercasino.model.GamesStrategies.SlotStrategy;
import org.example.cybercasino.model.constants.DatabaseConstants;
import org.example.cybercasino.model.constants.Games.DailySpinConstants;
import org.example.cybercasino.model.constants.Games.RouletteConstants;
import org.example.cybercasino.model.constants.Games.SlotMachine.FruitSlotMachineConstants;
import org.example.cybercasino.model.constants.Games.SlotMachine.MineSlotMachineConstants;
import org.example.cybercasino.model.constants.Games.SlotMachine.PremiumSlotMachineConstants;

public enum GameType {
    FRUIT_SLOT,
    PREMIUM_SLOT,
    MINE_SLOT,
    ROULETTE,
    DAILY_SPIN;


    public GameStrategy getGameStrategy() {
        return switch (this) {
            case FRUIT_SLOT, PREMIUM_SLOT, MINE_SLOT -> SlotStrategy.getInstance();
            case ROULETTE -> RouletteStrategy.getInstance();
            case DAILY_SPIN -> DailySpinStrategy.getInstance();
        };
    }

    public Object getGameConstants() {
        return switch (this) {
            case FRUIT_SLOT -> FruitSlotMachineConstants.getInstance();
            case PREMIUM_SLOT -> PremiumSlotMachineConstants.getInstance();
            case MINE_SLOT -> MineSlotMachineConstants.getInstance();
            case ROULETTE -> RouletteConstants.getInstance();
            case DAILY_SPIN -> DailySpinConstants.getInstance();
        };
    }
}