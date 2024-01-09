package org.example.cybercasino.controller.Details;

import org.example.cybercasino.controller.Authentication.utils.AuthToken;
import org.example.cybercasino.controller.Authentication.utils.AuthenticationUtils;
import org.example.cybercasino.controller.Details.utils.SimpleMatch;
import org.example.cybercasino.model.DAOs.GameHistoryDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.DTOs.utils.Match;
import org.example.cybercasino.model.constants.FrontendConstants;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = FrontendConstants.frontendUrl, allowCredentials = "true")
public class Details {
    private final int MAX_NUMBER_OF_LATEST_WINNING_MATCHES_FOR_LEADERBOARD = 5;

    @PostMapping("/getBalance")
    public double getUserBalance(@RequestBody AuthToken token) {
        User user = AuthenticationUtils.getUserFromToken(token.token);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
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
}
