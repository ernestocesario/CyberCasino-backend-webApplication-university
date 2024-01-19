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
    private static final String USERNAME_REGEX = "^[a-z0-9_]+$";

    private AuthenticationUtils() {}

    /*
    Code used with spring boot rest controller. We use servlets instead.

    @PostMapping("/register")
    public boolean register(@RequestBody SimpleUser simpleUser) {
        String email = simpleUser.email;
        String username = simpleUser.username;
        String hashedPassword = BCryptHashAlgorithm.getInstance().getHash(simpleUser.password);
        User user = new User(email, username, hashedPassword, 0, Date.valueOf("1970-01-01"), false);

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

        //check username length
        if (simpleUser.username.length() < MIN_USERNAME_LENGTH || simpleUser.username.length() > MAX_USERNAME_LENGTH)
            return false;

        //check username only contains lowercase letters, numbers and underscores
        if (!simpleUser.username.matches(USERNAME_REGEX))
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

            StringBuilder password = new StringBuilder();
            for (int i = 1; i < split.length; i++) {
                password.append(split[i]+":");
            }

            password.deleteCharAt(password.length()-1);
            for(int i = decodedToken.length() - 1; i >= 0 && decodedToken.charAt(i) == ':'; i--) {
                password.append(':');
            }

            return new Credentials(split[0], password.toString());
        }
        catch (Exception e) {
            return new Credentials("", "");
        }
    }
}