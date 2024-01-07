package org.example.cybercasino.controller.Games.utils;

import java.util.List;

public class GameResult {
    public List<String> result;
    public double balance;

    public GameResult(List<String> result, double balance) {
        this.result = result;
        this.balance = balance;
    }
}