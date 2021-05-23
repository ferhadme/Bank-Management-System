package com.farhad.controllers;

import com.farhad.database.DatabaseSource;
import com.farhad.models.Account;
import com.farhad.models.Customer;
import com.farhad.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class AddAccountController {
    @FXML
    private TextField accountIdTextField;
    @FXML
    private TextField accountNameTextField;
    @FXML
    private TextField amountOfMoneyTextField;
    @FXML
    private TextField otherDetailsTextField;
    @FXML
    private Button addAccountBtn;

    private boolean buttonActivation;

    private Customer customer;

    public void initialize() {
        customer = DatabaseSource.getInstance().getCustomer();
        accountIdTextField.setOnKeyTyped(event -> {
            if (modifyAccountId().length() == 16) {
                addAccountBtn.setCursor(Cursor.HAND);
                buttonActivation = true;
            } else {
                addAccountBtn.setCursor(Cursor.DEFAULT);
                buttonActivation = false;
            }
        });
        addAccountBtn.setOnAction(this::submit);
    }

    private boolean accountIdRegexValidation() {
        return modifyAccountId().matches("\\d{16}");
    }

    private String modifyAccountId() {
        return accountIdTextField.getText().trim().replaceAll("(\\s|-)*", "");
    }

    private void submit(ActionEvent event) {
        if (buttonActivation) {
            if (!accountIdRegexValidation()) {
                AlertUtils.showErrorAlert("Incorrect Input", "Your Account Id is not correct", "");
                return;
            }
            try {
                float amount = Float.parseFloat(amountOfMoneyTextField.getText().trim());
                Account account = new Account(modifyAccountId(), accountNameTextField.getText().trim(), amount,
                        otherDetailsTextField.getText().trim(), new ArrayList<>(), new ArrayList<>());
                DatabaseSource.getInstance().createNewAccount(account);
                customer.getAccounts().add(account);
            } catch (NumberFormatException e) {
                AlertUtils.showErrorAlert("Wrong Number Format",
                        "Please provide correct number format for amount of transaction", "");
            } finally {
                accountIdTextField.clear();
                accountNameTextField.clear();
                amountOfMoneyTextField.clear();
                otherDetailsTextField.clear();
            }
        }
    }

}
