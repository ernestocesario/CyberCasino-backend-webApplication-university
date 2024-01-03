package org.example.cybercasino.model.DAOs;

import jakarta.transaction.Transactional;
import org.example.cybercasino.model.DTOs.utils.Match;
import org.example.cybercasino.model.Database;
import org.example.cybercasino.model.constants.DatabaseConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameHistoryDAO {
    private static GameHistoryDAO instance;

    private GameHistoryDAO() {
    }

    public static GameHistoryDAO getInstance() {
        if (instance == null) {
            instance = new GameHistoryDAO();
        }
        return instance;
    }

    @Transactional
    public void addMatch(Match match) {
        long nextId = getNextId();

        try(Connection connection = Database.getInstance().createDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.INSERT_MATCH)) {

            preparedStatement.setLong(1, nextId);
            preparedStatement.setString(2, match.user().getEmail());
            preparedStatement.setString(3, match.gameType().name());
            preparedStatement.setDouble(4, match.amount());
            preparedStatement.setTimestamp(5, match.timestamp());

            preparedStatement.executeLargeUpdate();
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
        }
    }

    //private methods
    private long getNextId() {
        try(Connection connection = Database.getInstance().createDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.GET_LAST_ID_GAMEHISTORY)) {

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
}
