package com.farhad.controllers;

import com.farhad.database.DatabaseSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.Set;

public class RegistrationController {
    @FXML
    private ChoiceBox<String> customerTypesChoiceBox;

    public void initialize() {
        setCustomerTypesChoiceBoxItems();
    }

    private void setCustomerTypesChoiceBoxItems() {
        Set<String> keySet = DatabaseSource.CUSTOMER_TYPE_KEY_VALUE.keySet();
        ObservableList<String> items = FXCollections.observableArrayList(
                keySet.toArray(new String[0])
        );
        customerTypesChoiceBox.setItems(items);
    }
}
