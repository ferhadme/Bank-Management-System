package com.farhad.controllers;

import com.farhad.App;
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
    @FXML
    private Label devInfoLabel;

    // private boolean loginButtonActivation;

    public void initialize() {
        labelsPropertiesOnMouseEntered(forgotPasswordLabel);
        labelsPropertiesOnMouseExited(forgotPasswordLabel);
        labelsPropertiesOnMouseEntered(signUpLabel);
        labelsPropertiesOnMouseExited(signUpLabel);

        changeLoginBtnCursorOnKeyTyped(usernameTextField);
        changeLoginBtnCursorOnKeyTyped(passwordTextField);

        loginButton.setOnAction(this::login);

        labelsPropertiesOnMouseEntered(projectInfoLabel);
        labelsPropertiesOnMouseExited(projectInfoLabel);
        Tooltip.install(projectInfoLabel, new Tooltip("See source code of the project on GitHub"));
        projectInfoLabel.setOnMouseClicked(this::loadWebView);

        labelsPropertiesOnMouseEntered(devInfoLabel);
        labelsPropertiesOnMouseExited(devInfoLabel);
        Tooltip.install(devInfoLabel, new Tooltip("Contact with Farhad Mehdizada"));
        devInfoLabel.setOnMouseClicked(this::sendMailToDev);

        signUpLabel.setOnMouseClicked(this::registration);
        forgotPasswordLabel.setOnMouseClicked(this::forgotPassword);
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
                // loginButtonActivation = true;
            } else {
                loginButton.setCursor(Cursor.DEFAULT);
                // loginButtonActivation = false;
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
