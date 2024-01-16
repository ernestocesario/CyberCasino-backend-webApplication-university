package org.example.cybercasino.controller.Details;

import org.example.cybercasino.controller.Authentication.utils.AuthToken;
import org.example.cybercasino.controller.Details.utils.SimpleMatch;
import org.example.cybercasino.controller.Details.utils.SimpleTransaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ProfileController {

    private final UserController userController;
    public ProfileController(UserController userController) {
        this.userController = userController;
    }
    /*
    @GetMapping("/profile")
    public String showProfile(Model model, @RequestParam("token") String token) {

        List<SimpleTransaction> latestTransactions = userController.getLatestTransactionsByUser(token);
        List<SimpleMatch> latestMatches = userController.getLatestGamesResultsByUser(token);

        model.addAttribute("latestTransactions", latestTransactions);
        model.addAttribute("latestMatches", latestMatches);

        return "profile";
    }

     */
}
