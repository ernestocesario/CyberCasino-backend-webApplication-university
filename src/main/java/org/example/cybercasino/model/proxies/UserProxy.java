package org.example.cybercasino.model.proxies;

import org.example.cybercasino.model.DAOs.GameHistoryDAO;
import org.example.cybercasino.model.DAOs.TransactionHistoryDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.DTOs.utils.Match;
import org.example.cybercasino.model.DTOs.utils.Transaction;

import java.sql.Date;
import java.util.List;

public class UserProxy extends User {
    public UserProxy(User user) {
        super(user.getEmail(), user.getUsername(), user.getHashedPassword(), user.getBalance(), user.getLastDailySpin(), user.isBanned());
    }

    public UserProxy(String email, String username, String hashedPassword, double balance, Date lastDailySpin, boolean isBanned) {
        super(email, username, hashedPassword, balance, lastDailySpin, isBanned);
    }

    @Override
    public List<Transaction> getTransactionHistory() {
        if (!transactionHistory.isEmpty())
            return super.getTransactionHistory();

        List<Transaction> transactionHistoryParts = TransactionHistoryDAO.getTransactionHistoryByUser(this);

        if (transactionHistoryParts == null || transactionHistoryParts.isEmpty())
            return super.getTransactionHistory();
        transactionHistory.addAll(transactionHistoryParts);

        return super.getTransactionHistory();
    }

    @Override
    public List<Match> getGameHistory() {
        if (!gameHistory.isEmpty())
            return super.getGameHistory();

        List<Match> gameHistoryParts = GameHistoryDAO.getWinningMatchesByUser(this);

        if (gameHistoryParts == null || gameHistoryParts.isEmpty())
            return super.getGameHistory();
        gameHistory.addAll(gameHistoryParts);

        return super.getGameHistory();
    }
}
