package com.farhad.controllers;

import com.farhad.database.DatabaseSource;
import com.farhad.models.Account;
import com.farhad.models.Customer;
import com.farhad.utils.AlertGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static com.farhad.utils.RegexValidation.*;

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
            if (modifyNumber(accountIdTextField.getText().trim()).length() == 16) {
                addAccountBtn.setCursor(Cursor.HAND);
                buttonActivation = true;
            } else {
                addAccountBtn.setCursor(Cursor.DEFAULT);
                buttonActivation = false;
            }
        });
        addAccountBtn.setOnAction(this::submit);
    }

    private void submit(ActionEvent event) {
        if (buttonActivation) {
            if (!accountIdRegexValidation(accountIdTextField)) {
                AlertGenerator.showErrorAlert("Incorrect Input", "Your Account Id is not correct", "");
                return;
            }
            try {
                float amount = Float.parseFloat(amountOfMoneyTextField.getText().trim());
                Account account = new Account(modifyNumber(accountIdTextField.getText().trim()),
                        accountNameTextField.getText().trim(), amount,
                        otherDetailsTextField.getText().trim(), new ArrayList<>(), new ArrayList<>());
                DatabaseSource.getInstance().createNewAccount(account);
                customer.getAccounts().add(account);
                AlertGenerator.showInfoAlert("Successful", "New account has been added successfully", "");
            } catch (NumberFormatException e) {
                AlertGenerator.showErrorAlert("Wrong Number Format",
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
