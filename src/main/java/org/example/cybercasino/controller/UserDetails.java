package org.example.cybercasino.controller;

import org.example.cybercasino.controller.Authentication.AuthToken;
import org.example.cybercasino.controller.Authentication.Authentication;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.constants.FrontendConstants;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = FrontendConstants.frontendUrl, allowCredentials = "true")
public class UserDetails {
    @PostMapping("/getBalance")
    public double getBalance(@RequestBody AuthToken token) {
        User user = Authentication.getUserFromToken(token.token);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        return user.getBalance();
    }
}
