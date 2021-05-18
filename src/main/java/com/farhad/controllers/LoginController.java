package com.farhad.controllers;

import com.farhad.App;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Label forgotPasswordLabel;
    @FXML
    private Label signUpLabel;

    private boolean loginButtonActivation;

    public void initialize() {
        setupUI();
    }

    private void setupUI() {
        labelsPropertiesOnMouseEntered(forgotPasswordLabel);
        labelsPropertiesOnMouseExited(forgotPasswordLabel);
        labelsPropertiesOnMouseEntered(signUpLabel);
        labelsPropertiesOnMouseExited(signUpLabel);

        changeButtonCursorOnKeyTyped(usernameTextField);
        changeButtonCursorOnKeyTyped(passwordTextField);

        loginButton.setOnAction(event -> {
            if (loginButtonActivation) {
                System.out.println("Login is available");
            } else {
                System.out.println("Login is not available");
            }
        });
    }

    private void labelsPropertiesOnMouseEntered(Label label) {
        label.setOnMouseEntered(event -> {
            label.setUnderline(true);
            App.getScene().setCursor(Cursor.HAND);
        });
    }

    private void labelsPropertiesOnMouseExited(Label label) {
        label.setOnMouseExited(event -> {
            label.setUnderline(false);
            App.getScene().setCursor(Cursor.DEFAULT);
        });
    }

    private void changeButtonCursorOnKeyTyped(TextField textField) {
        textField.setOnKeyTyped(event -> {
            if (textField.getText().length() >= 1 && passwordTextField.getText().length() >= 8) {
                loginButton.setCursor(Cursor.HAND);
                loginButtonActivation = true;
            } else {
                loginButton.setCursor(Cursor.DEFAULT);
                loginButtonActivation = false;
            }
        });
    }

}
