package org.example.cybercasino.controller.Details;

import org.example.cybercasino.controller.Authentication.utils.AuthenticationUtils;
import org.example.cybercasino.controller.Details.utils.SimpleMatch;
import org.example.cybercasino.controller.Details.utils.SimpleTransaction;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.DTOs.utils.Match;
import org.example.cybercasino.model.DTOs.utils.Transaction;
import org.example.cybercasino.model.constants.MessageConstants;
import org.example.cybercasino.model.proxies.UserProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {
    @GetMapping("/profile")
    public String showProfile(@RequestParam String token, Model model) {
        User user = AuthenticationUtils.getUserFromToken(token);
        if (user == null) {
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND.name());
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("balance", user.getBalance());

        model.addAttribute("latestTransactions", convertToSimpleTransactions(user.getTransactionHistory()));
        model.addAttribute("latestMatches", convertToSimpleMatches(user.getGameHistory()));

        return "profile";
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