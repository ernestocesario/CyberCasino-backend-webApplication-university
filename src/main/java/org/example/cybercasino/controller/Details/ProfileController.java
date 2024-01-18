package org.example.cybercasino.controller.Details;

import org.example.cybercasino.controller.Authentication.utils.AuthenticationUtils;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.constants.MessageConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {
    @GetMapping("/profile")
    public String showProfile(@RequestParam String token, @RequestParam int transactionsToShow, @RequestParam int matchesToShow, Model model) {
        User user = AuthenticationUtils.getUserFromToken(token);
        if (user == null) {
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND.name());
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("balance", user.getBalance());

        model.addAttribute("latestTransactions", user.getTransactionHistory(transactionsToShow));
        model.addAttribute("latestMatches", user.getWinningGameHistory(matchesToShow));

        return "profile";
    }
}