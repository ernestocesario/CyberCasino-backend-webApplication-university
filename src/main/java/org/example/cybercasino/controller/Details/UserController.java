package org.example.cybercasino.controller.Details;

import org.example.cybercasino.controller.Authentication.utils.AuthToken;
import org.example.cybercasino.controller.Authentication.utils.AuthenticationUtils;
import org.example.cybercasino.controller.Details.utils.Player;
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
import java.util.Map;

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

    @PostMapping("/getListOfAllUsers")
    public List<Player> getListOfAllUsers(@RequestBody AuthToken token) {
        User user = AuthenticationUtils.getUserFromToken(token.token);
        if (user == null)
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND.name());

        if (!user.getUsername().equals("admin"))
            throw new RuntimeException(MessageConstants.USER_NOT_ADMIN.name());

        List<User> users = UserDAO.getAllUsers();
        return convertToPlayers(users);
    }

    @PostMapping("/getadditionalXLatestGamesResultsByUser")
    public List<SimpleMatch> getLatestGamesResultsByUser(@RequestBody Map<String, Object> body) {
        AuthToken token = new AuthToken((String) body.get("token"));
        long additionalMatchesToLoad = Long.parseLong((String) body.get("additionalMatchesToLoad"));

        User user = AuthenticationUtils.getUserFromToken(token.token);
        if (user == null)
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND.name());

        User userProxy = new UserProxy(user);

        if (additionalMatchesToLoad == 0)
            return convertToSimpleMatches(userProxy.getGameHistory());

        userProxy.getGameHistory(additionalMatchesToLoad);

        return convertToSimpleMatches(userProxy.getGameHistory());
    }

    @PostMapping("/getadditionalXLatestTransactionsByUser")
    public List<SimpleTransaction> getLatestTransactionsByUser(@RequestBody Map<String, Object> body) {
        AuthToken token = new AuthToken((String) body.get("token"));
        long additionalTransactionsToLoad = Long.parseLong((String) body.get("additionalTransactionsToLoad"));

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
    public boolean setUserBan(@RequestBody Map<String, Object> body) {
        AuthToken token = new AuthToken((String) body.get("token"));
        String username = (String) body.get("username");
        boolean isBanned = Boolean.parseBoolean((String) body.get("isBanned"));

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
        return true;
    }

    @PostMapping("/deposit")
    public boolean deposit(@RequestBody Map<String, Object> body) {
        AuthToken token = new AuthToken((String) body.get("token"));
        double amount = Double.parseDouble((String) body.get("amount"));

        User user = AuthenticationUtils.getUserFromToken(token.token);
        if (user == null) {
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND.name());
        }

        user.addBalance(amount);
        UserDAO.updateUser(user);

        return true;
    }

    @PostMapping("/withdraw")
    public boolean withdraw(@RequestBody Map<String, Object> body) {
        AuthToken token = new AuthToken((String) body.get("token"));
        double amount = Double.parseDouble((String) body.get("amount"));

        User user = AuthenticationUtils.getUserFromToken(token.token);
        if (user == null) {
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND.name());
        }

        try {
            user.subtractBalance(amount);
            UserDAO.updateUser(user);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
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

    private static List<Player> convertToPlayers(List<User> users) {
        List<Player> players = new ArrayList<>();

        for (User user : users)
            players.add(Player.convertToPlayer(user));

        return players;
    }
}
