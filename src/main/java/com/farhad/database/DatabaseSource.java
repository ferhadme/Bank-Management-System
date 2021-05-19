package com.farhad.database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseSource {
    private static DatabaseSource instance = new DatabaseSource();

    public static final String DB_NAME = "bank_management_system";
    public static final String PORT = "localhost:3306";
    public static final String CONNECTION_STRING = "jdbc:mysql://" + PORT + "/" + DB_NAME;
    public static final String USER = "farhad";
    public static final String PASSWORD = "Ferhad2002.";
    public static final Logger DB_ERROR_LOGGER = Logger.getLogger("Database Error");

    /*
     customer_types table
     */
    public static final Map<String, Integer> CUSTOMER_TYPE_KEY_VALUE = new HashMap<>();
    public static final String TABLE_CUSTOMER_TYPES = "customer_types";
    public static final String COLUMN_CUSTOMER_TYPE_CODE = "customer_type_code";
    public static final String COLUMN_CUSTOMER_TYPE_NAME = "customer_type_name";

    private Connection connection;

    private DatabaseSource() {}

    public static DatabaseSource getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
            init();
            return true;
        } catch (SQLException sqlException) {
            DB_ERROR_LOGGER.log(Level.SEVERE, "Couldn't connect to the database");
            return false;
        }
    }

    private void init() {
        try (Statement statement = connection.createStatement()) {
            ResultSet customerTypesKeyValue = statement.executeQuery(
                    "SELECT " + COLUMN_CUSTOMER_TYPE_CODE + ", " + COLUMN_CUSTOMER_TYPE_NAME + " " +
                    "FROM " + TABLE_CUSTOMER_TYPES
            );
            while (customerTypesKeyValue.next()) {
                CUSTOMER_TYPE_KEY_VALUE.put(
                        customerTypesKeyValue.getString(COLUMN_CUSTOMER_TYPE_NAME),
                        customerTypesKeyValue.getInt(COLUMN_CUSTOMER_TYPE_CODE)
                );
            }
        } catch (SQLException e) {
            DB_ERROR_LOGGER.log(Level.SEVERE, "Statement couldn't be executed");
        }
    }

    public boolean close() {
        if (connection != null) {
            try {
                connection.close();
                return true;
            } catch (SQLException sqlException) {
                DB_ERROR_LOGGER.log(Level.SEVERE, "Couldn't close the connection");
                return false;
            }
        }
        DB_ERROR_LOGGER.log(Level.SEVERE, "Connection isn't initialized");
        return false;
    }
}
