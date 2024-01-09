package org.example.cybercasino.model.constants;

public class DatabaseConstants {
    private DatabaseConstants() {}

    //Tables
    public static final String USERS_TABLE = "users";
    public static final String TRANSACTION_HISTORY_TABLE = "transactionhistory";
    public static final String GAME_HISTORY_TABLE = "gamehistory";

    //Users Table Columns Names
    public static final String USERS_TBL_EMAIL_COL = "email";
    public static final String USERS_TBL_USERNAME_COL = "username";
    public static final String USERS_TBL_PASSWORD_COL = "hashed_password";
    public static final String USERS_TBL_BALANCE_COL = "balance";
    public static final String USERS_TBL_DAILYSPIN_COL = "last_daily_spin";
    public static final String USERS_TBL_BANNED_COL = "banned";

    //Transaction History Table Columns Names
    public static final String TRANSACTION_HISTORY_TBL_ID_COL = "id";
    public static final String TRANSACTION_HISTORY_TBL_USER_COL = "user_email";
    public static final String TRANSACTION_HISTORY_TBL_AMOUNT_COL = "amount";
    public static final String TRANSACTION_HISTORY_TBL_TIME_COL = "time";

    //Game History Table Columns Names
    public static final String GAME_HISTORY_TBL_ID_COL = "id";
    public static final String GAME_HISTORY_TBL_USER_COL = "user_email";
    public static final String GAME_HISTORY_TBL_GAME_COL = "game_name";
    public static final String GAME_HISTORY_TBL_AMOUNT_COL = "amount";
    public static final String GAME_HISTORY_TBL_TIME_COL = "time";


    //Users queries
    public static final String GET_USER_BY_USERNAME = "SELECT * FROM " + USERS_TABLE + " WHERE " + USERS_TBL_USERNAME_COL + " = ?";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM " + USERS_TABLE + " WHERE " + USERS_TBL_EMAIL_COL + " = ?";
    public static final String ADD_USER = "INSERT INTO " + USERS_TABLE + " (" + USERS_TBL_EMAIL_COL + ", " + USERS_TBL_USERNAME_COL + ", " + USERS_TBL_PASSWORD_COL + ", " + USERS_TBL_BALANCE_COL + ", " + USERS_TBL_DAILYSPIN_COL + ", " + USERS_TBL_BANNED_COL + ") VALUES (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_USER = "UPDATE " + USERS_TABLE + " SET " + USERS_TBL_USERNAME_COL + " = ?, " + USERS_TBL_PASSWORD_COL + " = ?, " + USERS_TBL_BALANCE_COL + " = ?, " + USERS_TBL_DAILYSPIN_COL + " = ?, " + USERS_TBL_BANNED_COL + " = ? WHERE " + USERS_TBL_EMAIL_COL + " = ?";

    //Game History queries
    public static final String GET_LAST_ID_GAMEHISTORY = "SELECT MAX(" + GAME_HISTORY_TBL_ID_COL + ") FROM " + GAME_HISTORY_TABLE;
    public static final String INSERT_MATCH = "INSERT INTO " + GAME_HISTORY_TABLE + " (" + GAME_HISTORY_TBL_ID_COL + ", " + GAME_HISTORY_TBL_USER_COL + ", " + GAME_HISTORY_TBL_GAME_COL + ", " + GAME_HISTORY_TBL_AMOUNT_COL + ", " + GAME_HISTORY_TBL_TIME_COL + ") VALUES (?, ?, ?, ?, ?)";
    public static final String SELECT_MATCHES_BY_USER = "SELECT * FROM " + GAME_HISTORY_TABLE + " WHERE " + GAME_HISTORY_TBL_USER_COL + " = ?";
    public static final String SELECT_LAST_X_WINNING_MATCHES_BY_USER = "SELECT * FROM " + GAME_HISTORY_TABLE + " WHERE " + GAME_HISTORY_TBL_USER_COL + " = ? AND " + GAME_HISTORY_TBL_AMOUNT_COL + " > 0 ORDER BY " + GAME_HISTORY_TBL_TIME_COL + " DESC LIMIT ?";
    public static final String SELECT_LAST_X_WINNING_MATCHES_GLOBALLY = "SELECT * FROM " + GAME_HISTORY_TABLE + " WHERE " + GAME_HISTORY_TBL_AMOUNT_COL + " > 0 ORDER BY " + GAME_HISTORY_TBL_TIME_COL + " DESC LIMIT ?";

    //Transaction History queries
    public static final String GET_LAST_ID_TRANSACTIONHISTORY = "SELECT MAX(" + TRANSACTION_HISTORY_TBL_ID_COL + ") FROM " + TRANSACTION_HISTORY_TABLE;
    public static final String INSERT_TRANSACTION = "INSERT INTO " + TRANSACTION_HISTORY_TABLE + " (" + TRANSACTION_HISTORY_TBL_ID_COL + ", " + TRANSACTION_HISTORY_TBL_USER_COL + ", " + TRANSACTION_HISTORY_TBL_AMOUNT_COL + ", " + TRANSACTION_HISTORY_TBL_TIME_COL + ") VALUES (?, ?, ?, ?)";
    public static final String SELECT_LAST_X_TRANSACTIONS_BY_USER = "SELECT * FROM " + TRANSACTION_HISTORY_TABLE + " WHERE " + TRANSACTION_HISTORY_TBL_USER_COL + " = ? ORDER BY " + TRANSACTION_HISTORY_TBL_TIME_COL + " DESC LIMIT ?";
}
