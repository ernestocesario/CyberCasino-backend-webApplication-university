package org.example.cybercasino.model.constants.Games.SlotMachine;

public class FruitSlotMachineConstants extends SlotMachineConstants {
    private static FruitSlotMachineConstants instance;

    public static FruitSlotMachineConstants getInstance() {
        if (instance == null)
            instance = new FruitSlotMachineConstants();
        return instance;
    }

    private FruitSlotMachineConstants() {
        //winninPercentage = 12, fakeWinPercentage = 25
        super(4, 12, 40, 50, 2, new String[]{
                "banana",
                "bar",
                "blueberry",
                "cherry",
                "clover",
                "crown",
                "diamond",
                "golden_cherry",
                "golden_raspberry",
                "orange",
                "raspberry",
                "seven"
        });
    }
}
