package com.farhad.controllers;

import com.farhad.database.DatabaseSource;
import com.farhad.models.Customer;
import com.farhad.security.PBKDF2;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.farhad.utils.RegexValidation.passwordRegexValidation;

public class ChangePasswordDialogController {
    @FXML
    private PasswordField oldPasswordTextField;
    @FXML
    private PasswordField newPasswordTextField;
    @FXML
    private PasswordField confirmPasswordTextField;

    private Customer customer;

    public void initialize() {
        customer = DatabaseSource.getInstance().getCustomer();
    }

    public boolean changePassword() {
        if (!verifyNewPasswords() || !verifyOldPassword() || !passwordRegexValidation(newPasswordTextField)) {
            return false;
        } else {
            try {
                String password = PBKDF2.generateHashPassword(newPasswordTextField.getText());
                DatabaseSource.getInstance().changeCustomerPassword(password);
                customer.setPassword(password);
                return true;
            } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private boolean verifyNewPasswords() {
        return newPasswordTextField.getText().equals(confirmPasswordTextField.getText());
    }

    private boolean verifyOldPassword() {
        try {
            return PBKDF2.generateHashPassword(oldPasswordTextField.getText()).equals(customer.getPassword());
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
}

