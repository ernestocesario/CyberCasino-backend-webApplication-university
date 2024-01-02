package org.example.cybercasino.controller.Authentication;


import jakarta.servlet.http.HttpServletRequest;
import org.example.cybercasino.model.DAOs.UserDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.constants.FrontendConstants;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@CrossOrigin(origins = FrontendConstants.frontendUrl, allowCredentials = "true")
public class Authentication {
    @PostMapping("/login")
    public AuthToken login(@RequestBody SimpleUser simpleUser, HttpServletRequest req) {
        String email = simpleUser.email;
        String hashedPassword = simpleUser.password;
        String concat = email + ":" + hashedPassword;
        String token = Base64.getEncoder().encodeToString(concat.getBytes());
        User user = getUserByToken(token);
        if (user != null) {
            req.getSession().setAttribute("user", user);
            return new AuthToken(token);
        }
        return null;
    }

    @PostMapping("/logout")
    public boolean logout() {
        return true;
    }

    @PostMapping("/isAuthenticated")
    public boolean isAuthenticated(HttpServletRequest req) {
        String auth = req.getHeader("Authorization");
        if (auth == null) {
            return false;
        }

        String token = auth.substring("Basic ".length());
        return getUserByToken(token) != null;
    }

    //private methods
    private User getUserByToken(String token) {
        if (token == null) {
            return null;
        }
        String[] credentials = new String(Base64.getDecoder().decode(token)).split(":");
        String username = credentials[0];
        String password = credentials[1];

        User user = UserDAO.getInstance().findByEmail(username);
        if (user != null && user.getHashedPassword().equals(password))
            return user;
        return null;
    }
}