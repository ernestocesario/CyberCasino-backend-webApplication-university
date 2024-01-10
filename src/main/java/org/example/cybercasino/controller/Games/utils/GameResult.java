package org.example.cybercasino.controller.Games.utils;

import java.util.List;

public class GameResult {
    public final List<String> result;
    public final double balance;

    public GameResult(List<String> result, double balance) {
        this.result = result;
        this.balance = balance;
    }
}