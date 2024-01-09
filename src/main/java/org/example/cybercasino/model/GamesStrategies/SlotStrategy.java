package org.example.cybercasino.model.GamesStrategies;

import org.example.cybercasino.model.constants.Games.SlotMachine.SlotMachineConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlotStrategy extends GameStrategy {
    private static SlotStrategy instance;
    Random random = new Random();


    private SlotStrategy() {
        super();
    }

    public static SlotStrategy getInstance() {
        if (instance == null) {
            instance = new SlotStrategy();
        }
        return instance;
    }


    @Override
    protected boolean checkArgs(Object... args) {
        if (args.length != 1) {
            return false;
        }
        return args[0] instanceof SlotMachineConstants;
    }

    @Override
    protected boolean willWin(Object gameConstants) {
        SlotMachineConstants slotMachineConstants = (SlotMachineConstants) gameConstants;
        return random.nextInt(100) < slotMachineConstants.winningPercentage;
    }

    //TODO add fake win

    @Override
    protected List<String> generateResult(Object gameConstants, boolean isWin) {
        SlotMachineConstants slotMachineConstants = (SlotMachineConstants) gameConstants;

        List<String> result = new ArrayList<>();
        if (isWin) {
            int winningElementPos = random.nextInt(slotMachineConstants.numberOfElements);
            String winningElement = slotMachineConstants.elements[winningElementPos];

            for (int i = 0; i < slotMachineConstants.numberOfReels; i++) {
                result.add(winningElement);
            }
        }
        else {
            for (int i = 0; i < slotMachineConstants.numberOfReels; i++) {
                int rndElemPos = random.nextInt(slotMachineConstants.numberOfElements);
                result.add(slotMachineConstants.elements[rndElemPos]);
            }
            //evita che si generino risultati con tutti gli elementi uguali, anche se non è una vincita
            if (result.stream().distinct().count() == 1) {
                String elem = result.get(0);
                for (int i = 0; i < slotMachineConstants.elements.length; i++) {
                    if (!slotMachineConstants.elements[i].equals(elem)) {
                        result.set(slotMachineConstants.numberOfReels - 1, slotMachineConstants.elements[i]);
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    protected double calculateAmount(List<String> gameResult, double bet, boolean isWin, Object gameConstants) {
        SlotMachineConstants slotMachineConstants = (SlotMachineConstants) gameConstants;
        return isWin ? bet * slotMachineConstants.betMultiplier : bet;
    }
}
