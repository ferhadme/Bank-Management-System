package com.farhad.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class UserSettingsController {
    @FXML
    private Separator topSeparator;
    @FXML
    private Separator bottomSeparator;

    @FXML
    private TextField customerNameTextField;
    @FXML
    private TextField customerPhoneNumberTextField;
    @FXML
    private TextField customerEmailTextField;
    @FXML
    private TextField customerLoginTextField;
    @FXML
    private TextField otherDetailsTextField;
    @FXML
    private Button changePasswordBtn;
    @FXML
    private Button updateCustomerInfoBtn;

    public void initialize() {
        VBox.setMargin(topSeparator, new Insets(25, 0, 0, 0));
        VBox.setMargin(bottomSeparator, new Insets(0, 0, 25, 0));
        changePasswordBtn.setCursor(Cursor.HAND);

    }

}
