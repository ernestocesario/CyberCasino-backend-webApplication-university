package org.example.cybercasino.model.constants.Games.SlotMachine;

public class MineSlotMachineConstants extends SlotMachineConstants {
    private static MineSlotMachineConstants instance;

    public static MineSlotMachineConstants getInstance() {
        if (instance == null)
            instance = new MineSlotMachineConstants();
        return instance;
    }
    private MineSlotMachineConstants() {
        //winninPercentage = 8, fakeWinPercentage = 25
        super(4, 12, 40, 50, 3, new String[]{
                "star",
                "lightning",
                "chest",
                "key",
                "lock",
                "coin",
                "red_gem",
                "hearth",
                "purple_gem",
                "bag",
                "green_gem",
                "shield"
        });
    }
}
