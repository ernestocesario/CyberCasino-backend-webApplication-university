package org.example.cybercasino.model.DAOs;

import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.DTOs.utils.Transaction;
import org.example.cybercasino.model.Database;
import org.example.cybercasino.model.constants.DatabaseConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionHistoryDAO {
    private TransactionHistoryDAO() {}

    private static void addTransaction(Transaction transaction) {
        long nextId = Database.getInstance().getNextId(TransactionHistoryDAO.class);

        try(Connection connection = Database.getInstance().createDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.INSERT_TRANSACTION)) {

            preparedStatement.setLong(1, nextId);
            preparedStatement.setString(2, transaction.user().getEmail());
            preparedStatement.setDouble(3, transaction.amount());
            preparedStatement.setTimestamp(4, transaction.timestamp());

            preparedStatement.executeLargeUpdate();
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
        }
    }

    //method to get the last X (number) transactions of a user
    public static void getLastXTransactionsByUser(User user, int number) {
        String email = user.getEmail();

        try(Connection connection = Database.getInstance().createDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.SELECT_LAST_X_TRANSACTIONS_BY_USER)) {

            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, number);

            preparedStatement.executeLargeUpdate();
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
        }
    }
}
