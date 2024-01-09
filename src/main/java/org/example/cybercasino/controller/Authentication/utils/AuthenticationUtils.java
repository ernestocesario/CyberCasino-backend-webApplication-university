package org.example.cybercasino.controller.Authentication.utils;


import org.example.cybercasino.model.DAOs.UserDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.utils.BCryptHashAlgorithm;

import java.util.Base64;


public class AuthenticationUtils {
    private AuthenticationUtils() {}
    /*
    @PostMapping("/register")
    public boolean register(@RequestBody SimpleUser simpleUser) {
        String email = simpleUser.email;
        String username = simpleUser.username;
        String hashedPassword = BCryptHashAlgorithm.getInstance().getHash(simpleUser.password);
        User user = new User(email, username, hashedPassword, 10, Date.valueOf("1970-01-01"), false);

        //check if user already exists
        if (UserDAO.getInstance().findUserByUsername(username) != null || UserDAO.getInstance().findByEmail(email) != null)
            return false;

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
    */

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