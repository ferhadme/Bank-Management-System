package com.farhad.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseSource {
    private static DatabaseSource instance = new DatabaseSource();

    public static final String DB_NAME = "bank_management_system";
    public static final String PORT = "localhost:3306";
    public static final String CONNECTION_STRING = "jdbc:mysql://" + PORT + "/" + DB_NAME;
    public static final String USER = "farhad";
    public static final String PASSWORD = "Ferhad2002.";
    public static final Logger DB_CONNECTION_ERROR_LOGGER = Logger.getLogger("Database Connection Error");

    private Connection connection;

    private DatabaseSource() {}

    public static DatabaseSource getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
            return true;
        } catch (SQLException sqlException) {
            DB_CONNECTION_ERROR_LOGGER.log(Level.SEVERE, "Couldn't connect to the database");
            return false;
        }
    }

    public boolean close() {
        if (connection != null) {
            try {
                connection.close();
                return true;
            } catch (SQLException sqlException) {
                DB_CONNECTION_ERROR_LOGGER.log(Level.SEVERE, "Couldn't close the connection");
                return false;
            }
        }
        DB_CONNECTION_ERROR_LOGGER.log(Level.SEVERE, "Connection isn't initialized");
        return false;
    }
}
