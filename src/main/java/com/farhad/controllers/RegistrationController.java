package com.farhad.controllers;

import com.farhad.App;
import com.farhad.database.DatabaseSource;
import com.farhad.security.PBKDF2;
import com.farhad.utils.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationController {
    @FXML
    private ChoiceBox<String> customerTypesChoiceBox;
    @FXML
    private Button signUpButton;
    @FXML
    private Label loginLabel;

    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField confirmPassTextField;
    @FXML
    private TextField otherDetailsTextField;

    public void initialize() {
        setCustomerTypesChoiceBoxItems();
        loginLabelProperties();
        signUpButton.setOnAction(this::signUp);
    }

    private void setCustomerTypesChoiceBoxItems() {
        Set<String> keySet = DatabaseSource.CUSTOMER_TYPE_KEY_VALUE.keySet();
        ObservableList<String> items = FXCollections.observableArrayList(
                keySet.toArray(new String[0])
        );
        customerTypesChoiceBox.setItems(items);
        customerTypesChoiceBox.getSelectionModel().select(5);
    }

    private void loginLabelProperties() {
        loginLabel.setCursor(Cursor.HAND);
        loginLabel.setOnMouseEntered(event -> {
            loginLabel.setUnderline(true);
        });
        loginLabel.setOnMouseExited(event -> {
            loginLabel.setUnderline(false);
        });
        loginLabel.setOnMouseClicked(this::login);
    }

    private void signUp(ActionEvent event) {
        try {
            if (checkAllTextFields()) {
                String username = usernameTextField.getText().trim();
                String password = passwordTextField.getText();
                DatabaseSource.getInstance().signUpCustomer(
                        fullNameTextField.getText().trim(), replaceWhiteSpaces(phoneTextField.getText().trim()),
                        emailTextField.getText().trim(), username,
                        PBKDF2.generateHashPassword(password),
                        otherDetailsTextField.getText().trim(),
                        DatabaseSource.CUSTOMER_TYPE_KEY_VALUE.get(customerTypesChoiceBox.getValue())
                );
                loadDashboardOverview(username, password);
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            Logger.getLogger("Password Hashing Error").log(Level.SEVERE, "Password hashing error has happened");
        }
    }

    private void loadDashboardOverview(String username, String password) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/login.fxml"));
            Parent root = loader.load();
            LoginController controller = loader.getController();
            App.setRoot(root);
            controller.login(username, password);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger("IOException").log(Level.SEVERE, "Error has happened in login.fxml");
        }
    }

    private void login(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/login.fxml"));
            Parent root = loader.load();
            App.changeStageTitle("Login");
            App.setRoot(root);
        } catch (IOException e) {
            Logger.getLogger("IOException").log(Level.SEVERE, "Error has happened in login.fxml");
        }
    }

    private boolean checkAllTextFields() {
        if (!phoneNumberRegexValidation()) {
            AlertUtils.showErrorAlert("Incorrect Input", "Phone number is not correct",
                    "Please provide your phone number like (+000)00-000-00-00");
            return false;
        }
        if (!emailRegexValidation()) {
            AlertUtils.showErrorAlert("Incorrect Input", "Email is not correct",
                    "Please provide correct email address");
            return false;
        }
        if (!usernameRegexValidation()) {
            AlertUtils.showErrorAlert("Incorrect Input", "Username is not correct",
                    "Requirements:\n" +
                            "Length should be greater than or equal to 8\n" +
                            "Username could contain only letters, digits, ., _\n" +
                            "Digits and . could not be the first character of username\n" +
                            ". could not appear consecutively");
            return false;
        }
        if (usernameDBValidation()) {
            AlertUtils.showErrorAlert("Incorrect Input", "Username is already token",
                    "Please take another username");
            return false;
        }
        if (!passwordValidation()) {
            AlertUtils.showErrorAlert("Incorrect Input", "Username password is not safe",
                    "Safe password policy:\n" +
                            "At least 8 characters\n" +
                            "Contains at least one digit\n" +
                            "Contains at least one lower alpha character and one upper alpha character\n" +
                            "Contains at least one character within a set of special characters (@#%$^ etc)\n" +
                            "Does not contain space, tab, etc.");
        }
        if (!passwordConfirmation()) {
            AlertUtils.showErrorAlert("Incorrect Input", "Confirmation password is not correct",
                    "Please provide the same password");
            return false;
        }
        return true;
    }

    private boolean phoneNumberRegexValidation() {
        return replaceWhiteSpaces(phoneTextField.getText().trim()).matches("^\\(\\+\\d+\\)(-?\\d+)+$");
    }

    private boolean emailRegexValidation() {
        return emailTextField.getText().trim().matches("\\S+@(\\S*\\.?)+\\w+$");
    }

    private boolean usernameRegexValidation() {
        return usernameTextField.getText().trim().matches("^[a-zA-Z_]+(\\.?\\w+|_*\\w*)*$");
    }

    private boolean passwordValidation() {
        return passwordTextField.getText().matches(
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[.@#$%^&+=])(?=\\S+$).{8,}$"
        );
    }

    private boolean usernameDBValidation() {
        return DatabaseSource.getInstance().usernameExists(usernameTextField.getText().trim());
    }

    private boolean passwordConfirmation() {
        return passwordTextField.getText().equals(confirmPassTextField.getText());
    }

    private String replaceWhiteSpaces(String str) {
        return str.replaceAll("\\s", "");
    }


}
