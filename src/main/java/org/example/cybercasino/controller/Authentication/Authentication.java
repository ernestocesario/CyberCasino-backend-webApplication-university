package org.example.cybercasino.controller.Authentication;


import jakarta.servlet.http.HttpServletRequest;
import org.example.cybercasino.model.DAOs.UserDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.constants.FrontendConstants;
import org.example.cybercasino.utils.BCryptHashAlgorithm;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Date;

@RestController
@CrossOrigin(origins = FrontendConstants.frontendUrl, allowCredentials = "true")
public class Authentication {
    @PostMapping("/register")
    public boolean register(@RequestBody SimpleUser simpleUser) {
        String email = simpleUser.email;
        String username = simpleUser.username;
        String hashedPassword = BCryptHashAlgorithm.getInstance().getHash(simpleUser.password);
        User user = new User(email, username, hashedPassword, 10, new Date(0), false);
        return UserDAO.getInstance().addUser(user);
    }

    @PostMapping("/login")
    public AuthToken login(@RequestBody Credentials credentials) {
        String username = credentials.username;
        String plainPassword = credentials.password;
        String token = encodeToken(username, plainPassword);
        AuthToken authToken = new AuthToken();
        authToken.token = token;
        return userExists(username, plainPassword) ? authToken : null;
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
        return userExistsFromToken(token);
    }

    public static boolean userExistsFromToken(String token) {
        Credentials credentials = decodeToken(token);
        return userExists(credentials.username, credentials.password);
    }

    public static Credentials decodeToken(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token));
        String[] split = decodedToken.split(":");
        Credentials credentials = new Credentials();
        credentials.username = split[0];
        credentials.password = split[1];
        return credentials;
    }

    public static String encodeToken(String email, String hashedPassword) {
        String concat = email + ":" + hashedPassword;
        return Base64.getEncoder().encodeToString(concat.getBytes());
    }

    public static boolean userExists(String username, String hashedPassword) {
        User user = UserDAO.getInstance().findUserByUsername(username);
        return user != null && BCryptHashAlgorithm.getInstance().checkHash(hashedPassword, user.getHashedPassword());
    }

    public static User getUserFromToken(String token) {
        Credentials credentials = decodeToken(token);
        User user = UserDAO.getInstance().findUserByUsername(credentials.username);
        if (user != null && BCryptHashAlgorithm.getInstance().checkHash(credentials.password, user.getHashedPassword())) {
            return user;
        }
        return null;
    }
}