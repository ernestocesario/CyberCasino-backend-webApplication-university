package org.example.cybercasino.model.proxies;

import org.example.cybercasino.model.DAOs.GameHistoryDAO;
import org.example.cybercasino.model.DAOs.TransactionHistoryDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.DTOs.utils.Match;
import org.example.cybercasino.model.DTOs.utils.SimpleMatch;
import org.example.cybercasino.model.DTOs.utils.SimpleTransaction;
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
    public List<SimpleTransaction> getTransactionHistory(long ...totalTransactionToLoad) {
        long alreadyLoaded = transactionHistory.size();
        long howMany = howManyToLoad(alreadyLoaded, totalTransactionToLoad);
        long additionalToLoad = howMany - alreadyLoaded;

        if (howMany == alreadyLoaded)
            return super.getTransactionHistory();
        else if (howMany < alreadyLoaded) {
            return transactionHistory.subList(0, (int) howMany);
        }

        List<Transaction> transactionHistoryParts = TransactionHistoryDAO.getLatestXTransactionsByUserStartingFromLatestYTransactions(this, additionalToLoad, alreadyLoaded);

        if (transactionHistoryParts == null || transactionHistoryParts.isEmpty())
            return super.getTransactionHistory();

        for (var transaction : transactionHistoryParts)
            transactionHistory.add(SimpleTransaction.convertToSimpleTransaction(transaction));

        return super.getTransactionHistory();
    }

    @Override
    public List<SimpleMatch> getWinningGameHistory(long ...totalMatchesToLoad) {
        long alreadyLoaded = winningGameHistory.size();
        long howMany = howManyToLoad(alreadyLoaded, totalMatchesToLoad);
        long additionalToLoad = howMany - alreadyLoaded;

        if (howMany == alreadyLoaded)
            return super.getWinningGameHistory();
        else if (howMany < alreadyLoaded) {
            return winningGameHistory.subList(0, (int) howMany);
        }

        List<Match> winningGameHistoryParts = GameHistoryDAO.getLatestXWinningMatchesByUserStartingFromLatestYWinningMatches(this, additionalToLoad, alreadyLoaded);

        if (winningGameHistoryParts == null || winningGameHistoryParts.isEmpty())
            return super.getWinningGameHistory();

        for (var match : winningGameHistoryParts)
            winningGameHistory.add(SimpleMatch.convertToSimpleMatch(match));

        return super.getWinningGameHistory();
    }


    //private methods
    private long howManyToLoad(long alreadyLoaded, long ...additionalToLoads) {
        if (additionalToLoads.length == 1) {
            if (additionalToLoads[0] <= 0)
                throw new IllegalArgumentException("Argument must be greater than 0");
            else
                return additionalToLoads[0];
        }
        else if (additionalToLoads.length > 1)
            throw new IllegalArgumentException("Only one argument is allowed");
        return alreadyLoaded;
    }
}
