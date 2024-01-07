package org.example.cybercasino.model.constants.Games.SlotMachine;

public enum SlotMachineType {
    FRUIT,
    MINE,
    PREMIUM;

    public SlotMachineConstants getSlotMachineConstants() {
        return switch (this) {
            case FRUIT -> FruitSlotMachineConstants.getInstance();
            case MINE -> MineSlotMachineConstants.getInstance();
            case PREMIUM -> PremiumSlotMachineConstants.getInstance();
        };
    }
}
