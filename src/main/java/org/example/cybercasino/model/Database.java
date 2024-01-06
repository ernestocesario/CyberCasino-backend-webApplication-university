package org.example.cybercasino.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Database {
    private static Database instance = null;

    public String databaseUrl;
    public String databaseUsername;
    public String databasePassword;

    private Database() {
        Properties properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/databaseCredentials.properties")) {
            properties.load(fileInputStream);
            databaseUrl = properties.getProperty("url");
            databaseUsername = properties.getProperty("username");
            databasePassword = properties.getProperty("password");
        }
        catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            System.exit(1);
        }
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
