package com.farhad.controllers;

import com.farhad.App;
import com.farhad.database.DatabaseSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

import java.io.IOException;
import java.util.Optional;
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
        if (checkAllTextFields()) {
            // do database operations
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
            showAlter("Phone number is not correct",
                    "Please provide your phone number like (+000)00-000-00-00");
            return false;
        }
        if (!emailRegexValidation()) {
            showAlter("Email is not correct",
                    "Please provide correct email address");
            return false;
        }
        if (!usernameRegexValidation()) {
            showAlter("Username is not correct",
                    "Requirements:\n" +
                            "Length should be greater than or equal to 8\n" +
                            "Username could contain only letters, digits, ., _\n" +
                            "Digits and . could not be the first character of username\n" +
                            ". could not appear consecutively");
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
        String username = usernameTextField.getText().trim();
        return username.length() >= 8 && username.matches("^[a-zA-Z_]+(\\.?\\w+|_*\\w*)*$");
    }

    private boolean usernameDBValidation() {
        return false;
    }

    private boolean passwordValidation() {
        return false;
    }

    private boolean passwordConfirmation() {
        // should be the same as actual password
        return false;
    }

    private String replaceWhiteSpaces(String str) {
        return str.replaceAll("\\s", "");
    }

    private void showAlter(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Incorrect Input");
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            alert.close();
        }
    }
}
