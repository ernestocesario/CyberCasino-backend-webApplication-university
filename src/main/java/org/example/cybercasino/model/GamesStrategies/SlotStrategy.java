package org.example.cybercasino.model.GamesStrategies;

import org.example.cybercasino.model.constants.Games.SlotMachine.SlotMachineConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlotStrategy extends GameStrategy {
    private static SlotStrategy instance;
    Random random = new Random();


    private SlotStrategy() {
    }

    public static SlotStrategy getInstance() {
        if (instance == null) {
            instance = new SlotStrategy();
        }
        return instance;
    }

    @Override
    public List<Integer> generate(Object ...args) {
        if (args.length != 1 || !(args[0] instanceof SlotMachineConstants slotMachineConstants)) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        boolean win = willWin(slotMachineConstants);
        return generateResult(slotMachineConstants, win);
    }

    @Override
    public boolean isWinning(List<Integer> result) {
        return false;
    }

    //private methods
    private boolean willWin(SlotMachineConstants slotMachineConstants) {
        double probability = (double) slotMachineConstants.winningPercentage / 100.0;
        return random.nextInt(100) < probability;
    }

    private List<Integer> generateResult(SlotMachineConstants slotMachineConstants, boolean isWin) {
        List<Integer> result = new ArrayList<>();
        if (isWin) {
            int winningElement = random.nextInt(slotMachineConstants.numberOfElements);
            for (int i = 0; i < slotMachineConstants.numberOfReels; i++) {
                result.add(winningElement);
            }
        }
        else {
            for (int i = 0; i < slotMachineConstants.numberOfReels; i++) {
                result.add(random.nextInt(slotMachineConstants.numberOfElements));
            }
        }
        return result;
    }
}
