package com.farhad.utils;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegexValidation {

    private RegexValidation() {}

    public static boolean phoneNumberRegexValidation(TextField phoneTextField) {
        return modifyNumber(phoneTextField.getText().trim()).matches("^\\(\\+\\d+\\)(-?\\d+)+$");
    }

    public static boolean emailRegexValidation(TextField emailTextField) {
        return emailTextField.getText().trim().matches("\\S+@(\\S*\\.?)+\\w+$");
    }

    public static boolean usernameRegexValidation(TextField usernameTextField) {
        return usernameTextField.getText().trim().matches("^[a-zA-Z_]+(\\.?\\w+|_*\\w*)*$");
    }

    public static boolean passwordRegexValidation(PasswordField passwordTextField) {
        return passwordTextField.getText().matches(
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[.@#$%^&+=])(?=\\S+$).{8,}$"
        );
    }

    public static boolean accountIdRegexValidation(TextField accountIdTextField) {
        return modifyNumber(accountIdTextField.getText().trim()).matches("\\d{16}");
    }

    public static String modifyNumber(String str) {
        return str.replaceAll("(\\s|-)*", "");
    }
}
