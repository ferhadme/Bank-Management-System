package com.farhad.controllers;

import com.farhad.database.DatabaseSource;
import com.farhad.models.Account;
import com.farhad.models.Customer;
import com.farhad.models.Transaction;
import com.farhad.utils.TableViewUtils;
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
        Account mainAccount = customer.getAccounts().get(0);
        accountId.textProperty().bind(mainAccount.accountIdProperty());
        accountName.textProperty().bind(mainAccount.accountNameProperty());
        amountOfMoney.textProperty().bind(mainAccount.amountOfMoneyProperty().asString());
        otherAccountDetails.textProperty().bind(mainAccount.otherAccountDetailsProperty());
        TableViewUtils.constructIncomeAndOutcomeTableViews(incomes, outcomes);
        incomes.setItems(customer.getAccounts().get(0).getIncomes());
        outcomes.setItems(customer.getAccounts().get(0).getOutcomes());
    }

}
