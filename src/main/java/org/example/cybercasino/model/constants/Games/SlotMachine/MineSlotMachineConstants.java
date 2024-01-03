package org.example.cybercasino.model.constants.Games.SlotMachine;

public class MineSlotMachineConstants extends SlotMachineConstants {
    private static MineSlotMachineConstants instance;

    public static MineSlotMachineConstants getInstance() {
        if (instance == null)
            instance = new MineSlotMachineConstants();
        return instance;
    }
    private MineSlotMachineConstants() {
        super(4, 12, 10);
    }
}
