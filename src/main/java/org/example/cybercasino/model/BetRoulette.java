package org.example.cybercasino.model;

import java.util.Arrays;

public class BetRoulette {
    private Integer amt;
    private String type;
    private String[] numbers;
    public BetRoulette(int amt, String type, String numbers) {
        this.amt = amt;
        this.type = type;
        this.numbers = numbers.split(", "); //LO SPAZIO DOPO LA VIRGOLA è IMPORTANTE
    }
    public String getType() {return type;}
    public String[] getNumbers() {
        return numbers;
    }
    public Integer getAmt() {
        return amt;
    }
    // Metodo per verificare se la puntata ha vinto
    public boolean hasWon(String winningNumber) {
        for (String number : numbers) {
            if (number.equals(winningNumber)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "BetRoulette{" +
                "amt=" + amt +
                ", type='" + type + '\''+
                ", numbers=" + Arrays.toString(numbers) +
                '}';
    }
}
