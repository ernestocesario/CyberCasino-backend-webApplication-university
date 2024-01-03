package org.example.cybercasino.model.constants.Games.SlotMachine;

public abstract class SlotMachineConstants {
    public final int numberOfReels;
    public final int numberOfElements;
    public final int winningPercentage;

    public SlotMachineConstants(int numberOfReels, int numberOfElements, int winningPercentage) {
        this.numberOfReels = numberOfReels;
        this.numberOfElements = numberOfElements;
        this.winningPercentage = winningPercentage;
    }
}