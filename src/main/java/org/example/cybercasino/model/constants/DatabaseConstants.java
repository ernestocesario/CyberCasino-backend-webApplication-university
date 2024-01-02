package org.example.cybercasino.model.constants;

import org.springframework.beans.factory.annotation.Value;

public class DatabaseConstants {
    //Tables
    public static final String USERS_TABLE = "users";
    public static final String GAMES_TABLE = "games";
    public static final String TRANSACTION_HISTORY_TABLE = "transaction_history";
    public static final String GAME_HISTORY_TABLE = "game_history";

    //Users Table Columns Names
    public static final String USERS_TBL_EMAIL_COL = "id";
    public static final String USERS_TBL_USERNAME_COL = "username";
    public static final String USERS_TBL_PASSWORD_COL = "hashed_password";
    public static final String USERS_TBL_BALANCE_COL = "balance";
    public static final String USERS_TBL_CREATIONTIME_COL = "creation_time";
    public static final String USERS_TBL_DAILYSPIN_COL = "daily_spin_available";

    //Games Table Columns Names
    public static final String GAMES_TBL_ID_COL = "id";
    public static final String GAMES_TBL_NAME_COL = "common_name";

    //Transaction History Table Columns Names
    public static final String TRANSACTION_HISTORY_TBL_ID_COL = "id";
    public static final String TRANSACTION_HISTORY_TBL_USER_COL = "user_email";
    public static final String TRANSACTION_HISTORY_TBL_AMOUNT_COL = "amount";
    public static final String TRANSACTION_HISTORY_TBL_TIME_COL = "time";

    //Game History Table Columns Names
    public static final String GAME_HISTORY_TBL_ID_COL = "id";
    public static final String GAME_HISTORY_TBL_USER_COL = "user_email";
    public static final String GAME_HISTORY_TBL_GAME_COL = "game_id";
    public static final String GAME_HISTORY_TBL_AMOUNT_COL = "amount";


    //Get Users
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM " + USERS_TABLE + " WHERE email = ?";
}
