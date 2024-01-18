package org.example.cybercasino.model.DAOs;

import org.example.cybercasino.model.DTOs.User;
import org.example.cybercasino.model.DTOs.utils.Transaction;
import org.example.cybercasino.model.DTOs.utils.TransactionType;
import org.example.cybercasino.model.Database;
import org.example.cybercasino.model.constants.DatabaseConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryDAO {
    private TransactionHistoryDAO() {}

    public static void addTransaction(Transaction transaction) {
        long nextId = Database.getInstance().getNextId(TransactionHistoryDAO.class);

        double amount = (transaction.transactionType() == TransactionType.DEPOSIT) ? transaction.amount() : -transaction.amount();

        try(Connection connection = Database.getInstance().createDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.INSERT_TRANSACTION)) {

            preparedStatement.setLong(1, nextId);
            preparedStatement.setString(2, transaction.user().getEmail());
            preparedStatement.setDouble(3, amount);
            preparedStatement.setTimestamp(4, transaction.timestamp());

            preparedStatement.executeLargeUpdate();
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
        }
    }

    //method to get all transactions of a user
    public static List<Transaction> getTransactionHistoryByUser(User user) {
        List<Transaction> transactionHistory = new ArrayList<>();

        String email = user.getEmail();

        try(Connection connection = Database.getInstance().createDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.SELECT_TRANSACTIONS_BY_USER)) {

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                long id = resultSet.getLong(DatabaseConstants.TRANSACTION_HISTORY_TBL_ID_COL);
                User userTransaction = UserDAO.findByEmail(resultSet.getString(DatabaseConstants.TRANSACTION_HISTORY_TBL_USER_COL));
                double amount = resultSet.getDouble(DatabaseConstants.TRANSACTION_HISTORY_TBL_AMOUNT_COL);
                java.sql.Timestamp timestamp = resultSet.getTimestamp(DatabaseConstants.TRANSACTION_HISTORY_TBL_TIME_COL);

                TransactionType transactionType = amount > 0 ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;
                if (amount < 0) amount = -amount;
                transactionHistory.add(new Transaction(id, userTransaction, amount, transactionType, timestamp));
            }
            return transactionHistory;
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
            return null;
        }
    }

    //method to get the latest X (number) transactions of a user but discarding the latest Y (number) transactions
    public static List<Transaction> getLatestXTransactionsByUserStartingFromLatestYTransactions(User user, long number, long discard) {
        List<Transaction> transactionHistory = new ArrayList<>();

        String email = user.getEmail();

        try(Connection connection = Database.getInstance().createDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.SELECT_LATEST_X_TRANSACTIONS_BY_USER)) {

            preparedStatement.setString(1, email);
            preparedStatement.setLong(2, number);
            preparedStatement.setLong(3, discard);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                long id = resultSet.getLong(DatabaseConstants.TRANSACTION_HISTORY_TBL_ID_COL);
                User userTransaction = UserDAO.findByEmail(resultSet.getString(DatabaseConstants.TRANSACTION_HISTORY_TBL_USER_COL));
                double amount = resultSet.getDouble(DatabaseConstants.TRANSACTION_HISTORY_TBL_AMOUNT_COL);
                java.sql.Timestamp timestamp = resultSet.getTimestamp(DatabaseConstants.TRANSACTION_HISTORY_TBL_TIME_COL);

                TransactionType transactionType = amount > 0 ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;

                transactionHistory.add(new Transaction(id, userTransaction, amount, transactionType, timestamp));
            }
            return transactionHistory;
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
            return null;
        }
    }

    /* These methods are currently not used

    //method to get the last X (number) transactions of a user
    public static List<Transaction> getLastXTransactionsByUser(User user, long number) {
        List<Transaction> transactionHistory = new ArrayList<>();

        String email = user.getEmail();

        try(Connection connection = Database.getInstance().createDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.SELECT_LAST_X_TRANSACTIONS_BY_USER)) {

            preparedStatement.setString(1, email);
            preparedStatement.setLong(2, number);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                long id = resultSet.getLong(DatabaseConstants.TRANSACTION_HISTORY_TBL_ID_COL);
                User userTransaction = UserDAO.findByEmail(resultSet.getString(DatabaseConstants.TRANSACTION_HISTORY_TBL_USER_COL));
                double amount = resultSet.getDouble(DatabaseConstants.TRANSACTION_HISTORY_TBL_AMOUNT_COL);
                java.sql.Timestamp timestamp = resultSet.getTimestamp(DatabaseConstants.TRANSACTION_HISTORY_TBL_TIME_COL);

                TransactionType transactionType = amount > 0 ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;

                transactionHistory.add(new Transaction(id, userTransaction, amount, transactionType, timestamp));
            }
            return transactionHistory;
        }
        catch (SQLException e) {
            Database.getInstance().fatalDatabaseError(e);
            return null;
        }
    }
     */
}
