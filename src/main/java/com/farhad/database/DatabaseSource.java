package com.farhad.database;

import com.farhad.models.Account;
import com.farhad.models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /*
    customers table
     */
    public static final String TABLE_CUSTOMERS = "customers";
    public static final String COLUMN_CUSTOMER_LOGIN = "customer_login";
    public static final String COLUMN_CUSTOMER_NAME = "customer_name";
    public static final String COLUMN_CUSTOMER_PHONE = "customer_phone";
    public static final String COLUMN_CUSTOMER_EMAIL = "customer_email";
    public static final String COLUMN_CUSTOMER_PASSWORD = "customer_password";
    public static final String COLUMN_OTHER_DETAILS = "other_details";
    public static final String COLUMN_CUSTOMER_TYPE_CODE_FK = "customer_types_code";

    /*
    models
     */
    private Customer customer;

    private Connection connection;

    private DatabaseSource() {}

    public static DatabaseSource getInstance() {
        return instance;
    }

    public Customer getCustomer() {
        return customer;
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

    public boolean usernameExists(String username) {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT EXISTS(SELECT * FROM " + TABLE_CUSTOMERS +
                    " WHERE " + COLUMN_CUSTOMER_LOGIN + " = '" + username + "') as is_contain;");
            rs.next();
            int exist = rs.getInt("is_contain");
            return exist == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            DB_ERROR_LOGGER.log(Level.SEVERE, "Statement couldn't be executed");
        }
        return false;
    }

    public void signUpCustomer(String name, String phone, String email, String login, String password,
                               String other_details, int customerTypeCode) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO " + TABLE_CUSTOMERS +
                    " (" + COLUMN_CUSTOMER_NAME + ", " + COLUMN_CUSTOMER_PHONE + ", " + COLUMN_CUSTOMER_EMAIL +
                    ", " + COLUMN_CUSTOMER_LOGIN + ", " + COLUMN_CUSTOMER_PASSWORD + ", " + COLUMN_OTHER_DETAILS +
                    ", " + COLUMN_CUSTOMER_TYPE_CODE_FK + ") VALUES ('" + name + "', '" + phone + "', '" + email +
                    "', '" + login + "', '" + password + "', '" + other_details + "', " + customerTypeCode + ");");
            // create initial main account
            createInitMainAccount();
        } catch (SQLException e) {
            DB_ERROR_LOGGER.log(Level.SEVERE, "Statement couldn't be executed");
        }
    }

    public boolean verifyCustomerPassword(String username, String password) {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT " + COLUMN_CUSTOMER_PASSWORD + " FROM " +
                    TABLE_CUSTOMERS + " WHERE " + COLUMN_CUSTOMER_LOGIN + " = '" + username + "';");
            rs.next();
            String realPassword = rs.getString(COLUMN_CUSTOMER_PASSWORD);
            return realPassword.equals(password);
        } catch (SQLException e) {
            e.printStackTrace();
            DB_ERROR_LOGGER.log(Level.SEVERE, "Statement couldn't be executed");
        }
        return false;
    }

    public void loginCustomer(String username) {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT " + COLUMN_CUSTOMER_NAME + ", " + COLUMN_CUSTOMER_PHONE +
                    ", " + COLUMN_CUSTOMER_EMAIL + ", " + COLUMN_CUSTOMER_LOGIN + ", " + COLUMN_CUSTOMER_PASSWORD +
                    ", " + COLUMN_OTHER_DETAILS + " FROM " + TABLE_CUSTOMERS + " WHERE " + COLUMN_CUSTOMER_LOGIN +
                    " = '" + username + "';");
            rs.next();
            List<Account> accounts = getAccountsOfUser(username);
            customer = new Customer(rs.getString(COLUMN_CUSTOMER_NAME), rs.getString(COLUMN_CUSTOMER_PHONE),
                    rs.getString(COLUMN_CUSTOMER_EMAIL), rs.getString(COLUMN_CUSTOMER_LOGIN),
                    rs.getString(COLUMN_CUSTOMER_PASSWORD), rs.getString(COLUMN_OTHER_DETAILS), accounts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Account> getAccountsOfUser(String username) {
        List<Account> accounts = new ArrayList<>();
        // fetch all accounts and return
        return accounts;
    }

    private void createInitMainAccount() {
        // create init main account
        // insert it into database
    }

}
