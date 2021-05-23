package com.farhad.controllers;

import com.farhad.database.DatabaseSource;
import com.farhad.models.Account;
import com.farhad.models.Customer;
import com.farhad.models.Transaction;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OverviewController {
    @FXML
    private TableView<Transaction> incomes;
    @FXML
    private TableView<Transaction> outcomes;

    @FXML
    private Label accountId;
    @FXML
    private Label accountName;
    @FXML
    private Label amountOfMoney;
    @FXML
    private Label otherAccountDetails;

    private Customer customer;

    public void initialize() {
        customer = DatabaseSource.getInstance().getCustomer();
        constructTableViews();
        Account mainAccount = customer.getAccounts().get(0);
        accountId.textProperty().bind(mainAccount.accountIdProperty());
        accountName.textProperty().bind(mainAccount.accountNameProperty());
        amountOfMoney.textProperty().bind(mainAccount.amountOfMoneyProperty().asString());
        otherAccountDetails.textProperty().bind(mainAccount.otherAccountDetailsProperty());
    }

    private void constructTableViews() {
        incomes.setPlaceholder(new Label("No transaction made yet"));
        outcomes.setPlaceholder(new Label("No transaction made yet"));
        incomes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        outcomes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Transaction, String> incomeAccountId = new TableColumn<>("Account Id");
        incomeAccountId.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        incomeAccountId.setSortable(false);
        TableColumn<Transaction, String> incomeDestAccountId = new TableColumn<>("Destination Account Id");
        incomeDestAccountId.setCellValueFactory(new PropertyValueFactory<>("destinationAccountId"));
        incomeDestAccountId.setSortable(false);
        TableColumn<Transaction, Float> incomeAmountOfTransaction = new TableColumn<>("Amount of Transaction");
        incomeAmountOfTransaction.setCellValueFactory(new PropertyValueFactory<>("amountOfTransaction"));

        TableColumn<Transaction, String> outcomeAccountId = new TableColumn<>("Account Id");
        outcomeAccountId.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        outcomeAccountId.setSortable(false);
        TableColumn<Transaction, String> outcomeDestAccountId = new TableColumn<>("Destination Account Id");
        outcomeDestAccountId.setCellValueFactory(new PropertyValueFactory<>("destinationAccountId"));
        outcomeDestAccountId.setSortable(false);
        TableColumn<Transaction, Float> outcomeAmountOfTransaction = new TableColumn<>("Amount of Transaction");
        outcomeAmountOfTransaction.setCellValueFactory(new PropertyValueFactory<>("amountOfTransaction"));

        incomes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        outcomes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        incomes.getColumns().addAll(incomeAccountId, incomeDestAccountId, incomeAmountOfTransaction);
        outcomes.getColumns().addAll(outcomeAccountId, outcomeDestAccountId, outcomeAmountOfTransaction);
        incomes.setItems(customer.getAccounts().get(0).getIncomes());
        outcomes.setItems(customer.getAccounts().get(0).getOutcomes());
    }
}
