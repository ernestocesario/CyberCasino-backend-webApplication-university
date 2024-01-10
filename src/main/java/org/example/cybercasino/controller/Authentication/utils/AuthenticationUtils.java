package org.example.cybercasino.controller.Authentication.utils;


import org.example.cybercasino.model.DAOs.UserDAO;
import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.utils.BCryptHashAlgorithm;

import java.util.Base64;


public class AuthenticationUtils {
    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MAX_USERNAME_LENGTH = 12;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 64;
    private static final String EMAIL_REGEX = "^([a-z0-9_\\.\\+-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";

    private AuthenticationUtils() {}

    /*
    Code used with spring boot rest controller

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


    public static String encodeToken(String email, String hashedPassword) {
        String concat = email + ":" + hashedPassword;
        return Base64.getEncoder().encodeToString(concat.getBytes());
    }

    public static User getUserFromToken(String token) {
        Credentials credentials = decodeToken(token);
        User user = UserDAO.findByUsername(credentials.username());
        if (user != null && BCryptHashAlgorithm.getInstance().checkHash(credentials.password(), user.getHashedPassword())) {
            return user;
        }
        return null;
    }

    public static boolean checkRegistrationFields(SimpleUser simpleUser) {
        //check email
        if (!simpleUser.email.matches(EMAIL_REGEX))
            return false;

        //check username
        if (simpleUser.username.length() < MIN_USERNAME_LENGTH || simpleUser.username.length() > MAX_USERNAME_LENGTH)
            return false;

        //check password
        if (simpleUser.password.length() < MIN_PASSWORD_LENGTH || simpleUser.password.length() > MAX_PASSWORD_LENGTH)
            return false;

        return true;
    }

    //private methods
    private static Credentials decodeToken(String token) {
        try {
            String decodedToken = new String(Base64.getDecoder().decode(token));
            String[] split = decodedToken.split(":");
            return new Credentials(split[0], split[1]);
        }
        catch (Exception e) {
            return new Credentials("", "");
        }
    }
}