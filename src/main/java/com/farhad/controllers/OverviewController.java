package com.farhad.controllers;

import com.farhad.database.DatabaseSource;
import javafx.fxml.FXML;

public class OverviewController {

    public void initialize() {
        System.out.println(DatabaseSource.getInstance().getCustomer());
    }
}
