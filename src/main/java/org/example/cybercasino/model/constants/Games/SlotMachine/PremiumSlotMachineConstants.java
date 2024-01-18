package org.example.cybercasino.model.constants.Games.SlotMachine;

public class PremiumSlotMachineConstants extends SlotMachineConstants {
    private static PremiumSlotMachineConstants instance;

    public static PremiumSlotMachineConstants getInstance() {
        if (instance == null)
            instance = new PremiumSlotMachineConstants();
        return instance;
    }

    private PremiumSlotMachineConstants() {
        //winninPercentage = 5, fakeWinPercentage = 25
        super(5, 12, 40, 50, 4, new String[]{
                "peach",
                "bag",
                "bar",
                "melon",
                "apple",
                "cherry",
                "ruby",
                "lemon",
                "777",
                "banana",
                "grape",
                "strawberry"
        });
    }
}
