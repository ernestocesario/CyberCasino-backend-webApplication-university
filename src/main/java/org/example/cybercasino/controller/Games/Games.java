package org.example.cybercasino.controller.Games;

import org.example.cybercasino.controller.Authentication.utils.AuthenticationUtils;
import org.example.cybercasino.controller.Authentication.utils.Credentials;
import org.example.cybercasino.controller.Games.utils.GameInformation;
import org.example.cybercasino.controller.Games.utils.GameResult;
import org.example.cybercasino.utils.GeneratedGame;
import org.example.cybercasino.model.DAOs.GameHistoryDAO;
import org.example.cybercasino.model.DAOs.UserDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.DTOs.utils.Match;
import org.example.cybercasino.model.constants.Games.GameType;
import org.example.cybercasino.model.GamesStrategies.GameStrategy;
import org.example.cybercasino.model.constants.FrontendConstants;
import org.example.cybercasino.model.constants.Games.SlotMachine.SlotMachineType;
import org.example.cybercasino.model.constants.MessageConstants;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.*;
import java.sql.Date;

@RestController
@CrossOrigin(origins = FrontendConstants.frontendUrl, allowCredentials = "true")
public class Games {
    @PostMapping("/play")
    public GameResult play(@RequestBody GameInformation gameInformation) {
        if (!checkArgumentsValidity(gameInformation))
            throw new IllegalArgumentException(MessageConstants.INVALID_ARGUMENTS);

        User user = AuthenticationUtils.getUserFromToken(gameInformation.getSessionToken());
        if (user == null)
            throw new IllegalArgumentException(MessageConstants.USER_NOT_FOUND);

        if(!checkUserBalance(user, gameInformation))
            throw new IllegalArgumentException(MessageConstants.USER_BALANCE_INSUFFICIENT);

        checkGameRules(user, gameInformation);

        //generate game result
        GeneratedGame generatedGame = resultGenerator(gameInformation);

        //update user balance
        updateUserBalance(user, generatedGame);

        //update user in database
        UserDAO.updateUser(user);

        //add match to database
        addMatchToGameHistory(user, gameInformation.getGameType(), generatedGame);

        return new GameResult(generatedGame.gameResult(), user.getBalance());
    }



    //private methods
    private boolean checkArgumentsValidity(GameInformation gameInformation) {
        if (gameInformation != null && gameInformation.getSessionToken() != null && gameInformation.getGameType() != null) {

            switch (gameInformation.getGameType()) {
                //if gameType is slot machine, check that additionalInfo is an instance of SlotMachineType
                case SLOT_MACHINE:
                {
                    try {
                        SlotMachineType.valueOf(gameInformation.getAdditionalInfo());
                    }
                    catch (IllegalArgumentException e) {
                        return false;
                    }
                }
                //if gameType is roulette, horseRace, check that betOn is not null or empty
                case ROULETTE:
                    return gameInformation.getBetOn() != null && !gameInformation.getBetOn().isEmpty();
            }

            //if gameType isn't DAILY_SPIN, check that bet is greater than 0
            return gameInformation.getGameType() == GameType.DAILY_SPIN || gameInformation.getBet() > 0;
        }
        return false;
    }

    private void checkGameRules(User user, GameInformation gameInformation) {
        if (gameInformation.getGameType() == GameType.DAILY_SPIN) {
            LocalDate todayUTC = LocalDate.now(ZoneOffset.UTC).atStartOfDay().toLocalDate();


            if (user.getLastDailySpin().before(Date.valueOf(todayUTC)))
                user.setLastDailySpin(Date.valueOf(todayUTC));
            else
                throw new RuntimeException(MessageConstants.DAILY_SPIN_ALREADY_PLAYED);
        }
    }

    private boolean checkUserBalance(User user, GameInformation gameInformation) {
        if (gameInformation.getGameType() == GameType.DAILY_SPIN) {
            return true;
        }

        return user.getBalance() >= gameInformation.getBet();
    }

    private GeneratedGame resultGenerator(GameInformation gameInformation) {
        GameType gameType = gameInformation.getGameType();

        Object gameConstants;
        if (gameType == GameType.SLOT_MACHINE) {
            SlotMachineType slotMachineType = SlotMachineType.valueOf(gameInformation.getAdditionalInfo());
            gameConstants = gameType.getGameConstants(slotMachineType);
        }
        else {
            gameConstants = gameType.getGameConstants();

        }

        GameStrategy generator = gameType.getGameStrategy();

        return generator.generate(gameInformation.getBet(), gameConstants);
    }

    private void updateUserBalance(User user, GeneratedGame generatedGame) {
        double amount = generatedGame.amount();
        if (generatedGame.isWin())
            user.addBalance(amount);
        else
            user.subtractBalance(amount);
    }

    private void addMatchToGameHistory(User user, GameType gameType, GeneratedGame generatedGame) {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC")));
        double matchAmount = generatedGame.isWin() ? generatedGame.amount() : -generatedGame.amount();

        Match match = new Match(user, gameType, matchAmount, timestamp);
        GameHistoryDAO.addMatch(match);
    }
}
