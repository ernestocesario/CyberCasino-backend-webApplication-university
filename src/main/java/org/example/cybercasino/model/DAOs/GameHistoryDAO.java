package org.example.cybercasino.model.DAOs;

import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.DTOs.utils.Match;
import org.example.cybercasino.model.Database;
import org.example.cybercasino.model.constants.DatabaseConstants;
import org.example.cybercasino.model.constants.Games.GameType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameHistoryDAO {
    private GameHistoryDAO() {
    }

    public static void addMatch(Match match) {
        long nextId = Database.getInstance().getNextId(GameHistoryDAO.class);

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

    //method to get the last X (number) winning match of a user
    public static List<Match> getLastXWinningMatchesByUser(User user, int number) {
        List<Match> matches = new ArrayList<>();

        String email = user.getEmail();

        try(Connection connection = Database.getInstance().createDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.SELECT_LAST_X_WINNING_MATCHES_BY_USER)) {

            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, number);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                User userMatch = UserDAO.findByEmail(resultSet.getString(DatabaseConstants.GAME_HISTORY_TBL_USER_COL));
                GameType gameType = GameType.valueOf(resultSet.getString(DatabaseConstants.GAME_HISTORY_TBL_GAME_COL));
                double amount = resultSet.getDouble(DatabaseConstants.GAME_HISTORY_TBL_AMOUNT_COL);
                java.sql.Timestamp timestamp = resultSet.getTimestamp(DatabaseConstants.GAME_HISTORY_TBL_TIME_COL);

                matches.add(new Match(userMatch, gameType, amount, timestamp));
            }
            return matches;
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
            return null;
        }
    }

    //method to get the last X (number) winning match globally
    public static List<Match> getLastXWinningMatchesGlobally(int number) {
        List<Match> matches = new ArrayList<>();

        try(Connection connection = Database.getInstance().createDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.SELECT_LAST_X_WINNING_MATCHES_GLOBALLY)) {

            preparedStatement.setInt(1, number);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                User user = UserDAO.findByEmail(resultSet.getString(DatabaseConstants.GAME_HISTORY_TBL_USER_COL));
                GameType gameType = GameType.valueOf(resultSet.getString(DatabaseConstants.GAME_HISTORY_TBL_GAME_COL));
                double amount = resultSet.getDouble(DatabaseConstants.GAME_HISTORY_TBL_AMOUNT_COL);
                java.sql.Timestamp timestamp = resultSet.getTimestamp(DatabaseConstants.GAME_HISTORY_TBL_TIME_COL);

                matches.add(new Match(user, gameType, amount, timestamp));
            }
            return matches;
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
            return null;
        }
    }
}
