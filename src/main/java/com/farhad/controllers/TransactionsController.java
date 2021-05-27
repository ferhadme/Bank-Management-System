package com.farhad.controllers;

import com.farhad.database.DatabaseSource;
import com.farhad.models.Account;
import com.farhad.models.Customer;
import com.farhad.models.Transaction;
import com.farhad.utils.AlertGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import static com.farhad.utils.RegexValidation.accountIdRegexValidation;
import static com.farhad.utils.RegexValidation.modifyNumber;

public class TransactionsController {
    @FXML
    private TextField destAccountIdTextField;
    @FXML
    private TextField amountOfMoneyTextField;
    @FXML
    private Button submitTransactionBtn;
    @FXML
    private ChoiceBox<Account> accountChoiceBox;

    private Customer customer;

    private boolean buttonActivation;

    public void initialize() {
        customer = DatabaseSource.getInstance().getCustomer();
        accountChoiceBox.setItems(customer.getAccounts());

        destAccountIdTextField.setOnKeyTyped(event -> {
            if (modifyNumber(destAccountIdTextField.getText().trim()).length() == 16) {
                submitTransactionBtn.setCursor(Cursor.HAND);
                buttonActivation = true;
            } else {
                submitTransactionBtn.setCursor(Cursor.DEFAULT);
                buttonActivation = false;
            }
        });
        amountOfMoneyTextField.setText(String.valueOf(0));
        submitTransactionBtn.setOnAction(this::submitTransaction);
    }

    private void submitTransaction(ActionEvent event) {
        if (buttonActivation) {
            if (!accountIdRegexValidation(destAccountIdTextField)) {
                AlertGenerator.showErrorAlert("Incorrect Input", "Your Account Id is not correct", "");
                return;
            }
            try {
                float amount = Float.parseFloat(amountOfMoneyTextField.getText().trim());
                Account account = accountChoiceBox.getValue();
                if (amount > account.getAmountOfMoney() || amount <= 0) {
                    AlertGenerator.showErrorAlert("Error", "Not Enough Money",
                            "Please use different account or make deposit to this account");
                    return;
                }
                Transaction transaction = new Transaction(account.getAccountId(),
                        modifyNumber(destAccountIdTextField.getText().trim()), "", amount);
                account.setAmountOfMoney(account.getAmountOfMoney() - amount);
                DatabaseSource.getInstance().updateAccountMoney(account.getAccountId(), -(amount));
                DatabaseSource.getInstance().updateAccountMoney(modifyNumber(destAccountIdTextField.getText().trim()), amount);
                DatabaseSource.getInstance().makeTransaction(transaction);
                account.getOutcomes().add(transaction);
                Account destinationAccountOfCustomer = checkOtherCustomerAccounts(modifyNumber(destAccountIdTextField.getText().trim()));
                if (destinationAccountOfCustomer != null) {
                    System.out.println("Condition is true");
                    System.out.println(destinationAccountOfCustomer);
                    destinationAccountOfCustomer.getIncomes().add(new Transaction(transaction.getAccountId(),
                            transaction.getDestinationAccountId(), "",amount));

                    destinationAccountOfCustomer.setAmountOfMoney(destinationAccountOfCustomer.getAmountOfMoney() + amount);
                }
                AlertGenerator.showInfoAlert("Successful", "Transaction has been made successfully", "");
            } catch (NumberFormatException e) {
                AlertGenerator.showErrorAlert("Wrong Number Format",
                        "Please provide correct number format for amount of transaction", "");
            } finally {
                destAccountIdTextField.clear();
                amountOfMoneyTextField.clear();
            }
        }
    }

    private Account checkOtherCustomerAccounts(String accountId) {
        for (Account account : customer.getAccounts()) {
            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }
        return null;
    }
}
