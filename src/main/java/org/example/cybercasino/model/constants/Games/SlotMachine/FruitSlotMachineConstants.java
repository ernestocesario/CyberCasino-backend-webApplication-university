package org.example.cybercasino.model.constants.Games.SlotMachine;

public class FruitSlotMachineConstants extends SlotMachineConstants {
    private static FruitSlotMachineConstants instance;

    public static FruitSlotMachineConstants getInstance() {
        if (instance == null)
            instance = new FruitSlotMachineConstants();
        return instance;
    }

    private FruitSlotMachineConstants() {
        super(4, 12, 12, 30, 2, new String[]{
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
