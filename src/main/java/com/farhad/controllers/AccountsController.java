package com.farhad.controllers;

import com.farhad.database.DatabaseSource;
import com.farhad.models.Account;
import com.farhad.models.Customer;
import com.farhad.models.Transaction;
import com.farhad.utils.TableViewUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    @FXML
    private VBox accountInfoVBox;

    private Customer customer;

    public void initialize() {
        VBox.setMargin(accountInfoVBox, new Insets(20, 0, 0, 0));
        customer = DatabaseSource.getInstance().getCustomer();
        TableViewUtils.constructIncomeAndOutcomeTableViews(incomes, outcomes);

        MenuItem delete = new MenuItem("Delete");
        MenuItem update = new MenuItem("Update");
        MenuItem deposit = new MenuItem("Deposit");
        MenuItem withdraw = new MenuItem("Withdraw");
        MenuItem incomesToExcel = new MenuItem("Write incomes to Excel");
        MenuItem outcomesToExcel = new MenuItem("Write outcomes to Excel");
        ContextMenu menu = new ContextMenu();
        menu.getItems().addAll(delete, update, deposit, withdraw, incomesToExcel, outcomesToExcel);
        
        accountsListView.setItems(customer.getAccounts());
        accountsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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
        accountsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Account item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getAccountName());
                }
            }
        });
    }
}
