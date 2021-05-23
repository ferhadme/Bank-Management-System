package com.farhad.controllers;

import com.farhad.database.DatabaseSource;
import com.farhad.models.Account;
import com.farhad.models.Customer;
import com.farhad.models.Transaction;
import com.farhad.utils.TableViewUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

public class AccountsController {
    @FXML
    private ListView<Account> accountsListView;

    @FXML
    private Label accountIdLabel;
    @FXML
    private Label accountNameLabel;
    @FXML
    private Label otherDetailsLabel;
    @FXML
    private Label amountOfMoney;

    @FXML
    private TableView<Transaction> incomes;
    @FXML
    private TableView<Transaction> outcomes;

    private Customer customer;

    public void initialize() {
        customer = DatabaseSource.getInstance().getCustomer();
        TableViewUtils.constructIncomeAndOutcomeTableViews(incomes, outcomes);
        accountsListView.setItems(customer.getAccounts());
        accountsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
            if (newItem != null) {
                accountIdLabel.textProperty().bind(newItem.accountIdProperty());
                accountNameLabel.textProperty().bind(newItem.accountNameProperty());
                otherDetailsLabel.textProperty().bind(newItem.otherAccountDetailsProperty());
                amountOfMoney.textProperty().bind(newItem.amountOfMoneyProperty().asString());
                incomes.setItems(newItem.getIncomes());
                outcomes.setItems(newItem.getOutcomes());
            }
        });
    }
}
