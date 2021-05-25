package com.farhad.controllers;

import com.farhad.App;
import com.farhad.database.DatabaseSource;
import com.farhad.security.PBKDF2;
import com.farhad.utils.AlertGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    @FXML
    private Label devInfoLabel;

     private boolean loginButtonActivation;

    public void initialize() {
        labelsPropertiesOnMouseEntered(forgotPasswordLabel);
        labelsPropertiesOnMouseExited(forgotPasswordLabel);
        forgotPasswordLabel.setCursor(Cursor.HAND);
        labelsPropertiesOnMouseEntered(signUpLabel);
        labelsPropertiesOnMouseExited(signUpLabel);
        signUpLabel.setCursor(Cursor.HAND);

        changeLoginBtnCursorOnKeyTyped(usernameTextField);
        changeLoginBtnCursorOnKeyTyped(passwordTextField);

        loginButton.setOnAction(this::login);

        labelsPropertiesOnMouseEntered(projectInfoLabel);
        labelsPropertiesOnMouseExited(projectInfoLabel);
        projectInfoLabel.setCursor(Cursor.HAND);
        Tooltip.install(projectInfoLabel, new Tooltip("See source code of the project on GitHub"));
        projectInfoLabel.setOnMouseClicked(this::loadWebView);

        labelsPropertiesOnMouseEntered(devInfoLabel);
        labelsPropertiesOnMouseExited(devInfoLabel);
        devInfoLabel.setCursor(Cursor.HAND);
        Tooltip.install(devInfoLabel, new Tooltip("Contact with Farhad Mehdizada"));
        devInfoLabel.setOnMouseClicked(this::sendMailToDev);

        signUpLabel.setOnMouseClicked(this::registration);
        forgotPasswordLabel.setOnMouseClicked(this::forgotPassword);
    }

    private void labelsPropertiesOnMouseEntered(Label label) {
        label.setOnMouseEntered(event -> {
            label.setUnderline(true);
        });
    }

    private void labelsPropertiesOnMouseExited(Label label) {
        label.setOnMouseExited(event -> {
            label.setUnderline(false);
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

    private void loadWebView(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/webview.fxml"));
            Parent root = fxmlLoader.load();
            App.changeStageTitle("Embedded Web Browser");
            App.setRoot(root);
            WebViewController controller = fxmlLoader.getController();
            controller.loadURL(WebViewController.SOURCE_CODE);
        } catch (IOException e) {
            Logger.getLogger("IOException").log(Level.SEVERE, "Error has happened in webview.fxml");
        }
    }

    private void sendMailToDev(MouseEvent event) {
        /*
        NOT IMPLEMENTED YET...
         */
    }

    private void login(ActionEvent event) {
        if (loginButtonActivation) {
            login(usernameTextField.getText(), passwordTextField.getText());
        }
    }

    public void login(String username, String password) {
        try {
            if (DatabaseSource.getInstance().customerDataExists(username, DatabaseSource.COLUMN_CUSTOMER_LOGIN)
                    && DatabaseSource.getInstance().verifyCustomerPassword(username, PBKDF2.generateHashPassword(password))) {
                DatabaseSource.getInstance().loginCustomer(username);
                loadDashBoardOverview();
            } else {
                AlertGenerator.showErrorAlert("Incorrect Input", "Username or password is incorrect",
                        "Please provide correct username and password");
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            Logger.getLogger("Password Hashing Error").log(Level.SEVERE, "Password hashing error has happened");
        }
    }

    private void loadDashBoardOverview() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/dashboard_overview.fxml"));
            Parent root = fxmlLoader.load();
            App.changeStageTitle("Dashboard Overview");
            App.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger("IOException").log(Level.SEVERE, "Error has happened in dashboard_overview.fxml");
        }
    }

    private void registration(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/registration.fxml"));
            Parent root = fxmlLoader.load();
            App.changeStageTitle("Registration");
            App.setRoot(root);
        } catch (IOException e) {
            Logger.getLogger("IOException").log(Level.SEVERE, "Error has happened in registration.fxml");
        }
    }

    private void forgotPassword(MouseEvent event) {

    }

}
