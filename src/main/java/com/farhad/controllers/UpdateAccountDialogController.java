package com.farhad.controllers;

import com.farhad.database.DatabaseSource;
import com.farhad.models.Account;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UpdateAccountDialogController {
    @FXML
    private TextField newAccountNameTextField;
    @FXML
    private TextField newOtherDetails;

    @FXML
    private TextField depositTextField;
    @FXML
    private TextField withdrawTextField;

    public void initialize() {

    }

    public boolean updateAccountInformation(Account account) {
        newAccountNameTextField.promptTextProperty().bind(account.accountNameProperty());
        newOtherDetails.promptTextProperty().bind(account.otherAccountDetailsProperty());
        account.setAccountName(newAccountNameTextField.getText().trim());
        account.setOtherAccountDetails(newOtherDetails.getText().trim());
        DatabaseSource.getInstance().updateAccount(account);
        return true;
    }

    public boolean deposit(Account account) {
        depositTextField.setPromptText("0");
        try {
            float amount = Float.parseFloat(depositTextField.getText().trim());
            if (amount > 0) {
                DatabaseSource.getInstance().updateAccountMoney(account.getAccountId(), amount);
                account.setAmountOfMoney(account.getAmountOfMoney() + amount);
                return true;
            }
        } catch (NumberFormatException ignored) {
        }
        return false;
    }

    public boolean withdraw(Account account) {
        withdrawTextField.setPromptText("0");
        try {
            float amount = Float.parseFloat(withdrawTextField.getText().trim());
            if (amount <= account.getAmountOfMoney()) {
                DatabaseSource.getInstance().updateAccountMoney(account.getAccountId(), amount);
                account.setAmountOfMoney(account.getAmountOfMoney() + amount);
                return true;
            }
        } catch (NumberFormatException ignored) {
        }
        return false;
    }
}
