package org.example.cybercasino.model.DAOs;

import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.Database;
import org.example.cybercasino.model.constants.DatabaseConstants;

import java.sql.*;

import static org.example.cybercasino.model.constants.DatabaseConstants.*;

public class UserDAO {
    private static UserDAO instance;

    private UserDAO() {
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public User findByEmail(String email) {
        try (Connection connection = Database.getInstance().createDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.GET_USER_BY_EMAIL)) {

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString(USERS_TBL_USERNAME_COL);
                String password = resultSet.getString(USERS_TBL_PASSWORD_COL);
                double balance = resultSet.getDouble(USERS_TBL_BALANCE_COL);
                boolean dailySpinAvailable = resultSet.getBoolean(USERS_TBL_DAILYSPIN_COL);
                Timestamp creationTime = resultSet.getTimestamp(USERS_TBL_CREATIONTIME_COL);

                return new User(email, username, password, balance, dailySpinAvailable, creationTime);
            }
            return null;
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
        }
        return null;
    }
}
