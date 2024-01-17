package org.example.cybercasino.model.GamesStrategies;

import org.example.cybercasino.model.constants.Games.RouletteConstants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class RouletteStrategy extends GameStrategy {
    private static RouletteStrategy instance;
    Random random = new Random();
    private RouletteStrategy() {
        super();
    }

    public static RouletteStrategy getInstance() {
        if (instance == null) {
            instance = new RouletteStrategy();
        }
        return instance;
    }

    @Override
    protected boolean checkArgs(List<Object> betOn, Object gameConstants) {
        //qui devi controllare che betOn sia una lista di ciò che ti serve
        boolean isBetOnValid = betOn.size() > 0 && betOn.get(0) instanceof LinkedHashMap;

        //se non usi costanti puoi togliere la riga sotto
        return gameConstants instanceof RouletteConstants && isBetOnValid;
    }
    /*
    ogni obj bet che arriva javaswheel.js ha questa struttura (valori di esempio):
    {
        amt: 10,
        type: "inside",
        numbers: "1,2,3"
    }
    */
    @Override
    protected boolean willWin(List<Object> betOn, Object gameConstants) {
        // posso fare il cast diretto in quanto ho controllato prima in checkArgs che gameConstants sia di tipo RouletteConstants
        RouletteConstants rouletteConstants = (RouletteConstants) gameConstants;

        //calcolo una percentuale di vittoria random, ma influenzata dal quantitativo di numeri su cui si punta,
        //in modo che se punto su più numeri ho più probabilità di vincere
        ArrayList<String> numbersBet = new ArrayList<>();
        for (Object bet : betOn) {
            LinkedHashMap<String,Object> betMap = (LinkedHashMap<String,Object>) bet;

            //mi prendo l'obj.numbers che contiene una stringa del tipo "1, 2, 3"
            for (String number : ((String) betMap.get("numbers")).split(", ")) {
                numbersBet.add(number); //mi salvo tutti i numeri su cui si è puntato
            }
        }
        //in questo modo se idealmente ho puntato su tutti i numeri ho il 100% di probabilità di vincere
        //se ne ho puntato su uno avrò il 1/37 di probabilità di vincere
        return random.nextInt(37) < numbersBet.size();
    }

    @Override
    protected List<String> generateResult(List<Object> betOn, boolean isWin, Object gameConstants) {
        RouletteConstants rouletteConstants = (RouletteConstants) gameConstants;

        //stessa cosa di willWin, mi creo l'array contenente tutti i numeri su cui si è puntato
        ArrayList<String> numbersBet = new ArrayList<>();
        for (Object bet : betOn) {
            LinkedHashMap<String,Object> betMap = (LinkedHashMap<String,Object>) bet;

            //mi prendo l'obj.numbers che contiene una stringa del tipo "1, 2, 3"
            for (String number : ((String) betMap.get("numbers")).split(", ")) {
                numbersBet.add(number); //mi salvo tutti i numeri su cui si è puntato
            }
        }

        List<String> result = new ArrayList<>();
        if (isWin) {
            //se ho vinto genero un numero random tra quelli su cui si è puntato
            result.add(numbersBet.get(random.nextInt(numbersBet.size())));
        }
        else {
            //se ho perso genero un numero tra i 37 sul quale non ho puntato
            String number = rouletteConstants.insideNumbers[random.nextInt(rouletteConstants.insideNumbers.length)];
            while (numbersBet.contains(number)) {
                number = rouletteConstants.insideNumbers[random.nextInt(rouletteConstants.insideNumbers.length)];
            }
            result.add(number);
        }
        System.out.println("Roulette result: " + result);
        return result;
    }

    @Override
    protected double calculateAmount(List<String> gameResult, double bet, List<Object> betOn, boolean isWin, Object gameConstants) {
        RouletteConstants rouletteConstants = (RouletteConstants) gameConstants;
        Integer totalBets=0;  //somma di tutte le puntate
        double totalWin=0; //somma di tutte le vincite


        for(Object betObj: betOn){ // per ogni objBet {amt: int, type: string, numbers: string}  es: {amt: 10, type: "inside", numbers: "1,2,3"}
            LinkedHashMap<String,Object> betMap = (LinkedHashMap<String,Object>) betObj;
            totalBets+=(Integer) betMap.get("amt"); //aggiungo alla somma totale la puntata di questo objBet
            for (String number : ((String) betMap.get("numbers")).split(", ")) {
                if (gameResult.contains(number)) { //se il numero estratto è tra quelli su cui si è puntato
                    //calcolo l'importo vinto
                    totalWin = (Integer) betMap.get("amt") * rouletteConstants.betMultiplier.get(betMap.get("type"));
                }
            }
        }
        System.out.println("vinto: " + totalWin + " puntato: " + totalBets);
        //double diff= totalWin-totalBets; //restituisco la differenza tra le vincite e le puntate
        return (isWin)? totalWin-totalBets : totalBets;
    }
}
