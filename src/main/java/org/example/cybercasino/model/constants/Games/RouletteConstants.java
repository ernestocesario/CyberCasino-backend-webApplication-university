package org.example.cybercasino.model.constants.Games;

import java.util.HashMap;
import java.util.Map;

public class RouletteConstants {
    public final String[] insideNumbers = {"0","1","2","3","4","5","6","7","8","9","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23","24","25","26","27",
            "28","29","30","31","32","33","34","35","36"};
    public final Map<String,Integer> betMultiplier = new HashMap<>();
    private static RouletteConstants instance;

    public static RouletteConstants getInstance() {
        if (instance == null)
            instance = new RouletteConstants();
        return instance;
    }

    private RouletteConstants() {
        initBetMultiplier();
    }

    private void initBetMultiplier(){
        betMultiplier.put("double_street", 5);
        betMultiplier.put("street", 11);
        betMultiplier.put("split", 17);
        betMultiplier.put("corner_bet", 8);
        betMultiplier.put("outside_low", 1);
        betMultiplier.put("outside_high", 1);
        betMultiplier.put("zero", 8);
        betMultiplier.put("inside_whole", 35);
        betMultiplier.put("outside_column", 2);
        betMultiplier.put("outside_dozen", 2);
        betMultiplier.put("outside_oerb", 1);
    }
}
