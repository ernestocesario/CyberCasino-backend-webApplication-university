package org.example.cybercasino.controller.Authentication;


import jakarta.servlet.http.HttpServletRequest;
import org.example.cybercasino.model.DAOs.UserDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.constants.FrontendConstants;
import org.example.cybercasino.utils.hashingAlgorithms.BCryptHashAlgorithm;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@CrossOrigin(origins = FrontendConstants.frontendUrl, allowCredentials = "true")
public class Authentication {
    @PostMapping("/register")
    public boolean register(@RequestBody SimpleUser simpleUser) {
        String email = simpleUser.email;
        String username = simpleUser.username;
        String hashedPassword = BCryptHashAlgorithm.getInstance().getHash(simpleUser.password);
        User user = new User(email, username, hashedPassword, 0, false);
        return UserDAO.getInstance().addUser(user);
    }

    @PostMapping("/login")
    public AuthToken login(@RequestBody Credentials credentials) {
        String email = credentials.email;
        String hashedPassword = credentials.password;
        String token = encodeToken(email, hashedPassword);
        return userExists(email, hashedPassword) ? new AuthToken(token) : null;
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
        return userExists(credentials.email, credentials.password);
    }

    public static Credentials decodeToken(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token));
        String[] split = decodedToken.split(":");
        Credentials credentials = new Credentials();
        credentials.email = split[0];
        credentials.password = split[1];
        return credentials;
    }

    public static String encodeToken(String email, String hashedPassword) {
        String concat = email + ":" + hashedPassword;
        return Base64.getEncoder().encodeToString(concat.getBytes());
    }

    public static boolean userExists(String email, String hashedPassword) {
        User user = UserDAO.getInstance().findByEmail(email);
        return user != null && user.getHashedPassword().equals(hashedPassword);
    }
}