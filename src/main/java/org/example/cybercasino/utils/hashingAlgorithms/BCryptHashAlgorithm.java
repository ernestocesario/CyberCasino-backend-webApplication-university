package org.example.cybercasino.utils.hashingAlgorithms;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptHashAlgorithm implements GenericHashAlgorithm {
    private static BCryptHashAlgorithm instance = null;
    private BCryptHashAlgorithm() {}

    public static BCryptHashAlgorithm getInstance() {
        if (instance == null) {
            instance = new BCryptHashAlgorithm();
        }
        return instance;
    }

    //Local Constants
    private final int BCRYPT_LOG_ROUND = 15;

    @Override
    public String getHash(String text) {
        return BCrypt.hashpw(text, BCrypt.gensalt(BCRYPT_LOG_ROUND));
    }

    @Override
    public boolean checkHash(String text, String hash) {
        return BCrypt.checkpw(text, hash);
    }
}
