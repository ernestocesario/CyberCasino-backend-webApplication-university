package org.example.cybercasino.model.constants.Games.SlotMachine;

public abstract class SlotMachineConstants {
    public final int numberOfReels;
    public final int numberOfElements;
    public final int winningPercentage;
    public final int betMultiplier;
    public final String[] elements;

    public SlotMachineConstants(int numberOfReels, int numberOfElements, int winningPercentage, int betMultiplier, String[] elements) {
        this.numberOfReels = numberOfReels;
        this.numberOfElements = numberOfElements;
        this.winningPercentage = winningPercentage;
        this.betMultiplier = betMultiplier;
        this.elements = elements;
    }
}