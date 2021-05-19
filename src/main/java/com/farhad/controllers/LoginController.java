package com.farhad.controllers;

import com.farhad.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    @FXML
    private Label projectInfoLabel;

    private boolean loginButtonActivation;

    public void initialize() {
        setupUI();
    }

    private void setupUI() {
        labelsPropertiesOnMouseEntered(forgotPasswordLabel);
        labelsPropertiesOnMouseExited(forgotPasswordLabel);
        labelsPropertiesOnMouseEntered(signUpLabel);
        labelsPropertiesOnMouseExited(signUpLabel);

        changeLoginBtnCursorOnKeyTyped(usernameTextField);
        changeLoginBtnCursorOnKeyTyped(passwordTextField);

        loginButton.setOnAction(event -> {
            if (loginButtonActivation) {
                System.out.println("Login is available");
            } else {
                System.out.println("Login is not available");
            }
        });

        labelsPropertiesOnMouseEntered(projectInfoLabel);
        labelsPropertiesOnMouseExited(projectInfoLabel);
        Tooltip.install(projectInfoLabel, new Tooltip("See source code of the project on GitHub"));
        projectInfoLabel.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/webview.fxml"));
                Parent root = fxmlLoader.load();
                App.setRoot(root);
                WebViewController controller = fxmlLoader.getController();
                controller.loadURL("http://www.google.com");
            } catch (IOException e) {
                Logger.getLogger("IOException").log(Level.SEVERE, "Root file is not found");
            }
        });
    }

    private void labelsPropertiesOnMouseEntered(Label label) {
        label.setOnMouseEntered(event -> {
            label.setUnderline(true);
            label.setCursor(Cursor.HAND);
        });
    }

    private void labelsPropertiesOnMouseExited(Label label) {
        label.setOnMouseExited(event -> {
            label.setUnderline(false);
            label.setCursor(Cursor.DEFAULT);
        });
    }

    private void changeLoginBtnCursorOnKeyTyped(TextField textField) {
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
