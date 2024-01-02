package org.example.cybercasino.model;

import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database instance = null;

    @Value("${spring.datasource.url}")
    public static String databaseUrl;

    @Value("${spring.datasource.username}")
    public static String databaseUsername;

    @Value("${spring.datasource.password}")
    public static String databasePassword;



    private Database() {
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection createDBConnection() throws SQLException {  //Connect with the DB
        return DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
    }

    public void fatalDatabaseError(Exception e) {
        System.err.println("Fatal database error: " + e.getMessage());
        System.exit(1);
    }
}
