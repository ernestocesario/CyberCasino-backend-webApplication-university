package org.example.cybercasino.controller.Details;

import org.example.cybercasino.controller.Authentication.utils.AuthToken;
import org.example.cybercasino.controller.Authentication.utils.AuthenticationUtils;
import org.example.cybercasino.controller.Details.utils.LeaderboardMatch;
import org.example.cybercasino.controller.Details.utils.Player;
import org.example.cybercasino.model.DTOs.utils.*;
import org.example.cybercasino.model.DAOs.GameHistoryDAO;
import org.example.cybercasino.model.DAOs.TransactionHistoryDAO;
import org.example.cybercasino.model.DAOs.UserDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.constants.FrontendConstants;
import org.example.cybercasino.model.constants.MessageConstants;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public List<LeaderboardMatch> getLatestGamesResults() {
        List<Match> matches = GameHistoryDAO.getLastXWinningMatchesGlobally(MAX_NUMBER_OF_LATEST_WINNING_MATCHES_FOR_LEADERBOARD);
        List<LeaderboardMatch> leaderboardMatches = new ArrayList<>();

        if (matches == null)
            return leaderboardMatches;

        for (Match match : matches)
            leaderboardMatches.add(LeaderboardMatch.convertToLeaderboardMatch(match));

        return leaderboardMatches;
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
        addTransactionToTransactionHistory(user, amount, TransactionType.DEPOSIT);

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
            addTransactionToTransactionHistory(user, amount, TransactionType.WITHDRAWAL);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    //private methods
    private static List<Player> convertToPlayers(List<User> users) {
        List<Player> players = new ArrayList<>();

        for (User user : users)
            players.add(Player.convertToPlayer(user));

        return players;
    }

    private void addTransactionToTransactionHistory(User user, double amount, TransactionType transactionType) {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")));

        Transaction transaction = new Transaction(-1, user, amount, transactionType, timestamp);
        TransactionHistoryDAO.addTransaction(transaction);

        if (!user.getTransactionHistory().isEmpty()) {
            user.getTransactionHistory().add(0, SimpleTransaction.convertToSimpleTransaction(transaction));
        }
    }
}
