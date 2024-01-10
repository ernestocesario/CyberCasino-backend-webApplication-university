package org.example.cybercasino.controller.Details;

import org.example.cybercasino.controller.Authentication.utils.AuthToken;
import org.example.cybercasino.controller.Authentication.utils.AuthenticationUtils;
import org.example.cybercasino.controller.Details.utils.SimpleMatch;
import org.example.cybercasino.controller.Details.utils.SimpleTransaction;
import org.example.cybercasino.model.DAOs.GameHistoryDAO;
import org.example.cybercasino.model.DAOs.UserDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.DTOs.utils.Match;
import org.example.cybercasino.model.DTOs.utils.Transaction;
import org.example.cybercasino.model.constants.FrontendConstants;
import org.example.cybercasino.model.constants.MessageConstants;
import org.example.cybercasino.model.proxies.UserProxy;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = FrontendConstants.frontendUrl, allowCredentials = "true")
public class UserController {
    private static final int MAX_NUMBER_OF_LATEST_WINNING_MATCHES_FOR_LEADERBOARD = 5;

    @PostMapping("/getBalance")
    public double getUserBalance(@RequestBody AuthToken token) {
        User user = AuthenticationUtils.getUserFromToken(token.token);
        if (user == null) {
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND.name());
        }
        return user.getBalance();
    }

    @GetMapping("/getLatestGamesResults")
    public List<SimpleMatch> getLatestGamesResults() {
        List<Match> matches = GameHistoryDAO.getLastXWinningMatchesGlobally(MAX_NUMBER_OF_LATEST_WINNING_MATCHES_FOR_LEADERBOARD);
        List<SimpleMatch> simpleMatches = new ArrayList<>();

        if (matches == null)
            return simpleMatches;

        for (Match match : matches)
            simpleMatches.add(SimpleMatch.convertToSimpleMatch(match));

        return simpleMatches;
    }

    @PostMapping("/getadditionalXLatestGamesResultsByUser")
    public List<SimpleMatch> getLatestGamesResultsByUser(@RequestBody AuthToken token, @RequestParam long additionalMatchesToLoad) {
        User user = AuthenticationUtils.getUserFromToken(token.token);
        if (user == null)
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND.name());

        User userProxy = new UserProxy(user);

        List<Match> gameHistory;
        if (additionalMatchesToLoad == 0)
            return convertToSimpleMatches(userProxy.getGameHistory());

        userProxy.getGameHistory(additionalMatchesToLoad);

        return convertToSimpleMatches(userProxy.getGameHistory());
    }

    @PostMapping("/getadditionalXLatestTransactionsByUser")
    public List<SimpleTransaction> getLatestTransactionsByUser(@RequestBody AuthToken token, @RequestParam long additionalTransactionsToLoad) {
        User user = AuthenticationUtils.getUserFromToken(token.token);
        if (user == null)
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND.name());

        User userProxy = new UserProxy(user);

        if (additionalTransactionsToLoad == 0)
            return convertToSimpleTransactions(userProxy.getTransactionHistory());

        userProxy.getTransactionHistory(additionalTransactionsToLoad);

        return convertToSimpleTransactions(userProxy.getTransactionHistory());
    }

    @PostMapping("/setUserBan")
    public void setUserBan(@RequestBody AuthToken token, @RequestParam String username, @RequestParam boolean isBanned) {
        User user = AuthenticationUtils.getUserFromToken(token.token);
        if (user == null)
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND.name());

        if (!user.getUsername().equals("admin"))
            throw new RuntimeException(MessageConstants.USER_NOT_ADMIN.name());

        User userToBan = UserDAO.findByUsername(username);
        if (userToBan == null)
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND.name());

        userToBan.setBanned(isBanned);

        UserDAO.updateUser(userToBan);
    }


    //private methods
    private static List<SimpleTransaction> convertToSimpleTransactions(List<Transaction> transactions) {
        List<SimpleTransaction> simpleTransactions = new ArrayList<>();

        for (Transaction transaction : transactions)
            simpleTransactions.add(SimpleTransaction.convertToSimpleTransaction(transaction));

        return simpleTransactions;
    }

    private static List<SimpleMatch> convertToSimpleMatches(List<Match> matches) {
        List<SimpleMatch> simpleMatches = new ArrayList<>();

        for (Match match : matches)
            simpleMatches.add(SimpleMatch.convertToSimpleMatch(match));

        return simpleMatches;
    }
}
