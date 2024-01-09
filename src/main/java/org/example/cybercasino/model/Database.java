package org.example.cybercasino.model;

import org.example.cybercasino.model.DAOs.GameHistoryDAO;
import org.example.cybercasino.model.DAOs.TransactionHistoryDAO;
import org.example.cybercasino.model.constants.DatabaseConstants;
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

    public long getNextId(Class<?> clazz) {
        String getNextIdQuery = getNextIdQueryByClass(clazz);
        try(Connection connection = createDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getNextIdQuery)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1) + 1;
            }
            return 1L;
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
            return -1L;
        }
    }

    //private methods
    private String getNextIdQueryByClass(Class<?> clazz) {
        return switch (clazz.getCanonicalName()) {
            case "org.example.cybercasino.model.DAOs.GameHistoryDAO" -> DatabaseConstants.GET_LAST_ID_GAMEHISTORY;
            case "org.example.cybercasino.model.DAOs.TransactionHistoryDAO" -> DatabaseConstants.GET_LAST_ID_TRANSACTIONHISTORY;
            default -> {
                fatalDatabaseError(new RuntimeException("No next id query for class " + clazz.getCanonicalName() + " found"));
                yield null;
            }
        };
    }
}
