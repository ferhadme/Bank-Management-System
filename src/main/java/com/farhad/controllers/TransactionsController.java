package com.farhad.controllers;

import com.farhad.database.DatabaseSource;
import com.farhad.models.Account;
import com.farhad.models.Customer;
import com.farhad.models.Transaction;
import com.farhad.utils.AlertUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

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
            if (modifyAccountId().length() == 16) {
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
            if (!accountIdRegexValidation()) {
                AlertUtils.showErrorAlert("Incorrect Input", "Your Account Id is not correct", "");
                return;
            }
            try {
                float amount = Float.parseFloat(amountOfMoneyTextField.getText().trim());
                Account account = accountChoiceBox.getValue();
                if (amount > account.getAmountOfMoney()) {
                    AlertUtils.showErrorAlert("Not Enough Money",
                            "Please use different account or make deposit to this account", "");
                    return;
                }
                Transaction transaction = new Transaction(account.getAccountId(), modifyAccountId(), "", amount);
                DatabaseSource.getInstance().makeTransaction(transaction);
                account.getOutcomes().add(transaction);
            } catch (NumberFormatException e) {
                AlertUtils.showErrorAlert("Wrong Number Format",
                        "Please provide correct number format for amount of transaction", "");
            } finally {
                destAccountIdTextField.clear();
                amountOfMoneyTextField.clear();
            }
        }
    }

    private boolean accountIdRegexValidation() {
        return destAccountIdTextField.getText().trim().matches("\\d{16}");
    }

    private String modifyAccountId() {
        return destAccountIdTextField.getText().trim().replaceAll("(-|\\s)+", "");
    }


}
