package org.example.cybercasino.model;

import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.proxies.UserProxy;

import java.util.HashMap;
import java.util.Map;

public class RuntimeUserDatabase {
    private static RuntimeUserDatabase instance = null;

    private Map<String, UserProxy> users = new HashMap<>();


    private RuntimeUserDatabase() {
    }

    public static RuntimeUserDatabase getInstance() {
        if (instance == null) {
            instance = new RuntimeUserDatabase();
        }
        return instance;
    }

    public UserProxy getUser(User user) {
        if (users.containsKey(user.getUsername())) {
            return users.get(user.getUsername());
        }
        else {
            UserProxy userProxy = new UserProxy(user);
            users.put(user.getUsername(), userProxy);
            return userProxy;
        }
    }
}
