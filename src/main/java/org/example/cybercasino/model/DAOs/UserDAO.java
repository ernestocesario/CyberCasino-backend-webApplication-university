package org.example.cybercasino.model.DAOs;

import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.Database;
import org.example.cybercasino.model.RuntimeUserDatabase;
import org.example.cybercasino.model.constants.DatabaseConstants;
import org.example.cybercasino.model.proxies.UserProxy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.cybercasino.model.constants.DatabaseConstants.*;

public class UserDAO {
    private UserDAO() {
    }

    public static UserProxy findByEmail(String email) {
        try (Connection connection = Database.getInstance().createDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.GET_USER_BY_EMAIL)) {

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString(USERS_TBL_USERNAME_COL);
                String password = resultSet.getString(USERS_TBL_PASSWORD_COL);
                double balance = resultSet.getDouble(USERS_TBL_BALANCE_COL);
                Date lastDailySpin = resultSet.getDate(USERS_TBL_DAILYSPIN_COL);
                boolean isBanned = resultSet.getBoolean(USERS_TBL_BANNED_COL);

                UserProxy userProxy = new UserProxy(email, username, password, balance, lastDailySpin, isBanned);
                return RuntimeUserDatabase.getInstance().getUser(userProxy);
            }
            return null;
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
        }
        return null;
    }

    public static UserProxy findByUsername(String username) {
        try (Connection connection = Database.getInstance().createDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.GET_USER_BY_USERNAME)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String email = resultSet.getString(USERS_TBL_EMAIL_COL);
                String password = resultSet.getString(USERS_TBL_PASSWORD_COL);
                double balance = resultSet.getDouble(USERS_TBL_BALANCE_COL);
                Date lastDailySpin = resultSet.getDate(USERS_TBL_DAILYSPIN_COL);
                boolean isBanned = resultSet.getBoolean(USERS_TBL_BANNED_COL);

                UserProxy userProxy = new UserProxy(email, username, password, balance, lastDailySpin, isBanned);
                return RuntimeUserDatabase.getInstance().getUser(userProxy);
            }
            return null;
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
        }
        return null;
    }

    public static boolean addUser(User user) {
        if (findByEmail(user.getEmail()) != null || findByUsername(user.getUsername()) != null) {
            return false;
        }

        try (Connection connection = Database.getInstance().createDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.ADD_USER)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getHashedPassword());
            preparedStatement.setDouble(4, user.getBalance());
            preparedStatement.setDate(5, user.getLastDailySpin());
            preparedStatement.setBoolean(6, user.isBanned());

            preparedStatement.executeLargeUpdate();
            return true;
        }

        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
            return false;
        }
    }

    public static boolean updateUser(User user) {
        if (findByEmail(user.getEmail()) == null) {
            return false;
        }

        try (Connection connection = Database.getInstance().createDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.UPDATE_USER)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getHashedPassword());
            preparedStatement.setDouble(3, user.getBalance());
            preparedStatement.setDate(4, user.getLastDailySpin());
            preparedStatement.setBoolean(5, user.isBanned());
            preparedStatement.setString(6, user.getEmail());

            preparedStatement.executeLargeUpdate();
            return true;
        }

        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
            return false;
        }
    }

    //get all users from database
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Database.getInstance().createDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.GET_ALL_USERS)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String email = resultSet.getString(USERS_TBL_EMAIL_COL);
                String username = resultSet.getString(USERS_TBL_USERNAME_COL);
                String password = resultSet.getString(USERS_TBL_PASSWORD_COL);
                double balance = resultSet.getDouble(USERS_TBL_BALANCE_COL);
                Date lastDailySpin = resultSet.getDate(USERS_TBL_DAILYSPIN_COL);
                boolean isBanned = resultSet.getBoolean(USERS_TBL_BANNED_COL);

                users.add(new User(email, username, password, balance, lastDailySpin, isBanned));
            }
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
        }
        return users;
    }
}
