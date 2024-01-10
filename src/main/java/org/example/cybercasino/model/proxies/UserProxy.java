package org.example.cybercasino.model.proxies;

import org.example.cybercasino.model.DAOs.GameHistoryDAO;
import org.example.cybercasino.model.DAOs.TransactionHistoryDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.DTOs.utils.Match;
import org.example.cybercasino.model.DTOs.utils.Transaction;

import java.sql.Date;
import java.util.List;

public class UserProxy extends User {
    private final int MIN_NUMBER_OF_MATCHES_TO_LOAD_IF_EMPTY = 10;
    private final int MIN_NUMBER_OF_TRANSACTIONS_TO_LOAD_IF_EMPTY = 10;

    public UserProxy(User user) {
        super(user.getEmail(), user.getUsername(), user.getHashedPassword(), user.getBalance(), user.getLastDailySpin(), user.isBanned());
    }

    public UserProxy(String email, String username, String hashedPassword, double balance, Date lastDailySpin, boolean isBanned) {
        super(email, username, hashedPassword, balance, lastDailySpin, isBanned);
    }

    @Override
    public List<Transaction> getTransactionHistory(long ...additionalTransactionsToLoad) {
        long howMany = 0;

        if (additionalTransactionsToLoad.length == 0) {
            if (transactionHistory.isEmpty())
                howMany = MIN_NUMBER_OF_TRANSACTIONS_TO_LOAD_IF_EMPTY;
            else
                return super.getTransactionHistory();
        }

        if (additionalTransactionsToLoad.length > 1)
            throw new IllegalArgumentException("Only one argument is allowed");

        if (howMany == 0)
            howMany = additionalTransactionsToLoad[0];

        long alreadyLoaded = transactionHistory.size();
        List<Transaction> transactionHistoryParts = TransactionHistoryDAO.getLatestXTransactionsByUserStartingFromLatestYTransactions(this, howMany, alreadyLoaded);

        if (transactionHistoryParts == null || transactionHistoryParts.isEmpty())
            return super.getTransactionHistory();
        transactionHistory.addAll(transactionHistoryParts);

        return super.getTransactionHistory();
    }

    @Override
    public List<Match> getGameHistory(long ...additionalMatchesToLoad) {
        long howMany = 0;

        if (additionalMatchesToLoad.length == 0) {
            if (gameHistory.isEmpty())
                howMany = MIN_NUMBER_OF_MATCHES_TO_LOAD_IF_EMPTY;
            else
                return super.getGameHistory();
        }

        if (additionalMatchesToLoad.length > 1)
            throw new IllegalArgumentException("Only one argument is allowed");

        if (howMany == 0)
            howMany = additionalMatchesToLoad[0];

        long alreadyLoaded = gameHistory.size();
        List<Match> gameHistoryParts = GameHistoryDAO.getLatestXWinningMatchesByUserStartingFromLatestYWinningMatches(this, howMany, alreadyLoaded);

        if (gameHistoryParts == null || gameHistoryParts.isEmpty())
            return super.getGameHistory();
        gameHistory.addAll(gameHistoryParts);

        return super.getGameHistory();
    }
}
