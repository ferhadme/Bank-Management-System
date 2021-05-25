package com.farhad.database;

import com.farhad.models.Account;
import com.farhad.models.Customer;
import com.farhad.models.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    public static final Map<String, Integer> ACCOUNT_TYPE_KEY_VALUE = new HashMap<>();
    public static final String TABLE_CUSTOMER_TYPES = "customer_types";
    public static final String COLUMN_CUSTOMER_TYPE_CODE = "customer_type_code";
    public static final String COLUMN_CUSTOMER_TYPE_NAME = "customer_type_name";

    /*
    account_types table
     */
    public static final String TABLE_ACCOUNT_TYPES = "account_types";
    public static final String COLUMN_ACCOUNT_TYPE_CODE = "account_type_code";
    public static final String COLUMN_ACCOUNT_TYPE_NAME = "account_type_name";

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
    accounts table
     */
    public static final String TABLE_ACCOUNTS = "accounts";
    public static final String COLUMN_ACCOUNT_ID = "account_id";
    public static final String COLUMN_ACCOUNT_NAME = "account_name";
    public static final String COLUMN_ACCOUNT_OTHER_DETAILS = "other_account_details";
    public static final String COLUMN_AMOUNT_OF_MONEY = "amount_of_money";
    public static final String COLUMN_CUSTOMER_ID = "customer_id";
    public static final int ACCOUNT_TYPE_CODE = 1;

    /*
    transactions table
     */
    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COLUMN_DEST_ACCOUNT_ID = "destination_account_id";
    public static final String COLUMN_AMOUNT_OF_TRANSACTION = "amount_of_transaction";
    public static final String COLUMN_TRANSACTION_TYPE_CODE = "transaction_type_code";
    public static final String TRANSACTION_TYPE_CODE = "5";

    /*
    models
     */
    private Customer customer;
    private String prevCustomerLogin;

    private Connection connection;

    private DatabaseSource() {}

    public static DatabaseSource getInstance() {
        return instance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setPrevCustomerLogin(String prevCustomerLogin) {
        this.prevCustomerLogin = prevCustomerLogin;
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
            ResultSet accountTypesKeyValue = statement.executeQuery("SELECT " + COLUMN_ACCOUNT_TYPE_CODE + ", " +
                    COLUMN_ACCOUNT_TYPE_NAME + " FROM " + TABLE_ACCOUNT_TYPES
            );
            while (accountTypesKeyValue.next()) {
                ACCOUNT_TYPE_KEY_VALUE.put(
                        accountTypesKeyValue.getString(COLUMN_ACCOUNT_TYPE_NAME).replace(" ", "_"),
                        accountTypesKeyValue.getInt(COLUMN_ACCOUNT_TYPE_CODE)
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

    public boolean customerDataExists(String data, String COLUMN_NAME) {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT EXISTS(SELECT * FROM " + TABLE_CUSTOMERS +
                    " WHERE " + COLUMN_NAME + " = '" + data + "') as is_contain;");
            rs.next();
            int exist = rs.getInt("is_contain");
            return exist == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            DB_ERROR_LOGGER.log(Level.SEVERE, "Statement couldn't be executed");
            return false;
        }
    }

    public boolean customerDataExistsOnUpdate(String data, String COLUMN_NAME) {
        try (Statement statement = connection.createStatement()) {
            System.out.println(COLUMN_NAME);
            System.out.println(data);
            System.out.println(customer.getLogin());
            ResultSet rs = statement.executeQuery("SELECT EXISTS(SELECT * FROM " + TABLE_CUSTOMERS +
                    " WHERE " + COLUMN_NAME + " = '" + data + "' AND " + COLUMN_CUSTOMER_LOGIN + "!='" + customer.getLogin() +
                    "') as is_contain;");
            rs.next();
            int exist = rs.getInt("is_contain");
            System.out.println(exist);
            return exist == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            DB_ERROR_LOGGER.log(Level.SEVERE, "Statement couldn't be executed");
            return false;
        }
    }

    public void signUpCustomer(Customer customer, int customerTypeCode) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO " + TABLE_CUSTOMERS +
                    " (" + COLUMN_CUSTOMER_NAME + ", " + COLUMN_CUSTOMER_PHONE + ", " + COLUMN_CUSTOMER_EMAIL +
                    ", " + COLUMN_CUSTOMER_LOGIN + ", " + COLUMN_CUSTOMER_PASSWORD + ", " + COLUMN_OTHER_DETAILS +
                    ", " + COLUMN_CUSTOMER_TYPE_CODE_FK + ") VALUES ('" + customer.getName() + "', '" +
                    customer.getPhoneNumber() + "', '" + customer.getEmail() + "', '" + customer.getLogin() + "', '" +
                    customer.getPassword() + "', '" + customer.getOtherDetails() + "', " + customerTypeCode + ");");
            createInitMainAccount(customer.getAccounts().get(0), customer.getLogin());
        } catch (SQLException e) {
            e.printStackTrace();
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
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT " + COLUMN_ACCOUNT_ID + ", " + COLUMN_ACCOUNT_NAME +
                    ", " + COLUMN_AMOUNT_OF_MONEY + ", " + COLUMN_ACCOUNT_OTHER_DETAILS + " FROM " +
                    TABLE_ACCOUNTS + " WHERE " + COLUMN_CUSTOMER_ID + " = (SELECT " + COLUMN_CUSTOMER_ID +
                    " FROM " + TABLE_CUSTOMERS + " WHERE " + COLUMN_CUSTOMER_LOGIN + " = '" + username + "');");
            while (rs.next()) {
                List<Transaction> incomes = getIncomesOfCustomer(rs.getString(COLUMN_ACCOUNT_ID));
                List<Transaction> outcomes = getOutcomesOfCustomer(rs.getString(COLUMN_ACCOUNT_ID));
                accounts.add(new Account(rs.getString(COLUMN_ACCOUNT_ID), rs.getString(COLUMN_ACCOUNT_NAME),
                        rs.getDouble(COLUMN_AMOUNT_OF_MONEY), rs.getString(COLUMN_ACCOUNT_OTHER_DETAILS), incomes, outcomes));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private void createInitMainAccount(Account mainAccount, String customerLogin) {
        try (Statement statement = connection.createStatement()) {
            System.out.println(ACCOUNT_TYPE_KEY_VALUE.get("Standart_Account"));
            statement.executeUpdate("INSERT INTO " + TABLE_ACCOUNTS + "(" + COLUMN_ACCOUNT_ID + ", " +
                    COLUMN_ACCOUNT_NAME + ", " + COLUMN_ACCOUNT_OTHER_DETAILS + ", " + COLUMN_ACCOUNT_TYPE_CODE + ", " +
                    COLUMN_AMOUNT_OF_MONEY + ", " + COLUMN_CUSTOMER_ID + ") VALUES ('" + mainAccount.getAccountId() + "', '" +
                    mainAccount.getAccountName() + "', '" + mainAccount.getOtherAccountDetails() + "', " +
                    ACCOUNT_TYPE_KEY_VALUE.get("Standart_Account") + ", " + mainAccount.getAmountOfMoney() +
                    ", (SELECT " + COLUMN_CUSTOMER_ID + " FROM " + TABLE_CUSTOMERS + " WHERE " + COLUMN_CUSTOMER_LOGIN +
                    "='" + customerLogin + "'));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Transaction> getIncomesOfCustomer(String accountId) {
        List<Transaction> transactions = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT " + COLUMN_DEST_ACCOUNT_ID + ", " + COLUMN_OTHER_DETAILS +
                    ", " + COLUMN_AMOUNT_OF_TRANSACTION + " FROM " + TABLE_TRANSACTIONS + " WHERE " +
                    COLUMN_DEST_ACCOUNT_ID + " = '" + accountId + "';");
            while (rs.next()) {
                transactions.add(new Transaction(accountId, rs.getString(COLUMN_DEST_ACCOUNT_ID),
                        rs.getString(COLUMN_OTHER_DETAILS), rs.getFloat(COLUMN_AMOUNT_OF_TRANSACTION)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private List<Transaction> getOutcomesOfCustomer(String accountId) {
     List<Transaction> transactions = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT " + COLUMN_DEST_ACCOUNT_ID + ", " + COLUMN_OTHER_DETAILS +
                    ", " + COLUMN_AMOUNT_OF_TRANSACTION + " FROM " + TABLE_TRANSACTIONS + " WHERE " +
                    COLUMN_ACCOUNT_ID + " = '" + accountId + "';");
            while (rs.next()) {
                transactions.add(new Transaction(accountId, rs.getString(COLUMN_DEST_ACCOUNT_ID),
                        rs.getString(COLUMN_OTHER_DETAILS), rs.getFloat(COLUMN_AMOUNT_OF_TRANSACTION)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void makeTransaction(Transaction transaction) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO " + TABLE_TRANSACTIONS + "(" + COLUMN_ACCOUNT_ID + ", " +
                    COLUMN_DEST_ACCOUNT_ID + ", " + COLUMN_TRANSACTION_TYPE_CODE + ", " + COLUMN_AMOUNT_OF_TRANSACTION + ", " +
                    COLUMN_OTHER_DETAILS + ") VALUES ('" + transaction.getAccountId() + "', '" + transaction.getDestinationAccountId() +
                    "', " + TRANSACTION_TYPE_CODE + ", " + transaction.getAmountOfTransaction() + ", '" + transaction.getOtherDetails() +
                    "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNewAccount(Account account) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO " + TABLE_ACCOUNTS + "(" + COLUMN_ACCOUNT_ID + ", " + COLUMN_ACCOUNT_NAME +
                    ", " + COLUMN_ACCOUNT_OTHER_DETAILS + ", " + COLUMN_ACCOUNT_TYPE_CODE + ", " + COLUMN_AMOUNT_OF_MONEY +
                    ", " + COLUMN_CUSTOMER_ID + ") VALUES ('" + account.getAccountId() + "', '" + account.getAccountName() +
                    "', '" + account.getOtherAccountDetails() + "', " + ACCOUNT_TYPE_CODE + ", " + account.getAmountOfMoney() +
                    ", (SELECT " + COLUMN_CUSTOMER_ID + " FROM " + TABLE_CUSTOMERS + " WHERE " + COLUMN_CUSTOMER_LOGIN +
                    "='" + customer.getLogin() + "'));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeCustomerPassword(String password) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE " + TABLE_CUSTOMERS + " SET " + COLUMN_CUSTOMER_PASSWORD + "='" +
                    password + "' WHERE " + COLUMN_CUSTOMER_LOGIN + "='" + customer.getLogin() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomerInfo() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE " + TABLE_CUSTOMERS + " SET " +
                    COLUMN_CUSTOMER_LOGIN + "='" + customer.getLogin() + "'," +
                    COLUMN_CUSTOMER_NAME + "='" + customer.getName() + "'," +
                    COLUMN_CUSTOMER_EMAIL + "='" + customer.getEmail() + "'," +
                    COLUMN_CUSTOMER_PHONE + "='" + customer.getPhoneNumber() + "'," +
                    COLUMN_OTHER_DETAILS + "='" + customer.getOtherDetails() + "' " +
                    "WHERE " + COLUMN_CUSTOMER_LOGIN + "='" + prevCustomerLogin + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + TABLE_CUSTOMERS + " WHERE " +
                    COLUMN_CUSTOMER_LOGIN + "='" + customer.getLogin() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAccountMoney(String accountId, float amount) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE " + TABLE_ACCOUNTS + " SET " +
                    COLUMN_AMOUNT_OF_MONEY + "=" + amount + " WHERE " +
                    COLUMN_ACCOUNT_ID + "='" + accountId + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(Account account) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + TABLE_ACCOUNTS + " WHERE " + COLUMN_ACCOUNT_ID + "='" +
                    account.getAccountId() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAccount(Account account) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE " + TABLE_ACCOUNTS + " SET " +
                    COLUMN_ACCOUNT_NAME + "='" + account.getAccountName() + "', " +
                    COLUMN_ACCOUNT_OTHER_DETAILS + "='" + account.getOtherAccountDetails() +
                    "' WHERE " + COLUMN_ACCOUNT_ID + "='" + account.getAccountId() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
