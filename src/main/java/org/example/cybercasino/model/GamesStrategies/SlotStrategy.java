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
    protected boolean checkArgs(List<Object> betOn, Object gameConstants) {
        return gameConstants instanceof SlotMachineConstants;
    }

    @Override
    protected boolean willWin(List<Object> betOn, Object gameConstants) {
        SlotMachineConstants slotMachineConstants = (SlotMachineConstants) gameConstants;
        return random.nextInt(100) < slotMachineConstants.winningPercentage;
    }

    //TODO add fake win

    @Override
    protected List<String> generateResult(List<Object> betOn, boolean isWin, Object gameConstants) {
        SlotMachineConstants slotMachineConstants = (SlotMachineConstants) gameConstants;

        List<String> result;
        if (isWin)
            result = generateWin(slotMachineConstants);
        else if (willFakeWin(slotMachineConstants)) {
            result = generateWin(slotMachineConstants);
            avoidFakeWin(result, slotMachineConstants);
        }
        else {
            result = generateRandomResult(slotMachineConstants);
            avoidFakeWin(result, slotMachineConstants);
        }
        return result;
    }

    @Override
    protected double calculateAmount(List<String> gameResult, double bet, List<Object> betOn, boolean isWin, Object gameConstants) {
        SlotMachineConstants slotMachineConstants = (SlotMachineConstants) gameConstants;
        return isWin ? bet * slotMachineConstants.betMultiplier : bet;
    }


    //calcola la probabilità per generare tutti i reel uguali tranne l'ultimo
    private boolean willFakeWin(SlotMachineConstants slotMachineConstants) {
        return random.nextInt(100) < slotMachineConstants.fakeWinPercentage ;
    }

    //evita che si generino risultati con tutti gli elementi uguali, anche se non è una vincita
    private void avoidFakeWin(List<String> result, SlotMachineConstants slotMachineConstants) {
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

    //genera una vincita
    private List<String> generateWin(SlotMachineConstants slotMachineConstants) {
        List<String> result = new ArrayList<>();
        int winningElementPos = random.nextInt(slotMachineConstants.numberOfElements);
        String winningElement = slotMachineConstants.elements[winningElementPos];

        for (int i = 0; i < slotMachineConstants.numberOfReels; i++) {
            result.add(winningElement);
        }
        return result;
    }

    //genera un risultato casuale
    private List<String> generateRandomResult(SlotMachineConstants slotMachineConstants) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < slotMachineConstants.numberOfReels; i++) {
            int rndElemPos = random.nextInt(slotMachineConstants.numberOfElements);
            result.add(slotMachineConstants.elements[rndElemPos]);
        }
        return result;
    }
}
