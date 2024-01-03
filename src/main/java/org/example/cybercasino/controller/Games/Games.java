package org.example.cybercasino.controller.Games;

import org.example.cybercasino.controller.Authentication.Authentication;
import org.example.cybercasino.controller.Authentication.Credentials;
import org.example.cybercasino.controller.Games.utils.GameInformation;
import org.example.cybercasino.model.DAOs.GameHistoryDAO;
import org.example.cybercasino.model.DAOs.UserDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.DTOs.utils.Match;
import org.example.cybercasino.model.DTOs.utils.MatchResult;
import org.example.cybercasino.model.GameType;
import org.example.cybercasino.model.GamesStrategies.GameStrategy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
public class Games {
    @PostMapping("/generateResult")
    public List<Integer> generateResult(@RequestBody GameInformation gameInformation) {
        //check arguments validity
        if (!checkArgumentsValidity(gameInformation)) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        String userToken = gameInformation.getSessionToken();
        GameType gameType = GameType.valueOf(gameInformation.getGameName());
        int bet = gameInformation.getBet();

        //check if user exist
        if (!Authentication.userExistsFromToken(userToken)) {
            throw new IllegalArgumentException("User does not exist");
        }

        Credentials credentials = Authentication.decodeToken(userToken);

        //check if user has enough money
        User user = UserDAO.getInstance().findByEmail(credentials.email);
        if (user.getBalance() < bet) {
            throw new IllegalArgumentException("User does not have enough money");
        }

        //subtract bet from user balance
        user.subtractBalance(bet);

        //generate result
        GameStrategy generator = gameType.getGameStrategy();
        List<Integer> result = generator.generate(gameType.getGameConstants());

        boolean isWin = generator.isWinning(result);
        //if user won, update balance
        if (isWin) {
            if (gameType == GameType.DAILY_SPIN) {
                //TODO add daily spin reward
            } else {
                user.addBalance(bet * 2);
            }
        }

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")));
        Match match = new Match(user, gameType, bet, isWin ? MatchResult.WIN : MatchResult.LOSS, timestamp);

        //add match to database
        GameHistoryDAO.getInstance().addMatch(match);

        return result;
    }



    //private methods
    private boolean checkArgumentsValidity(GameInformation gameInformation) {
        return gameInformation != null &&
                gameInformation.getSessionToken() != null &&
                gameInformation.getGameName() != null &&
                gameExists(gameInformation.getGameName()) &&
                gameInformation.getBet() > 0;
    }

    private boolean gameExists(String gameName) {
        try {
            GameType.valueOf(gameName);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
