package com.farhad.controllers;

import com.farhad.database.DatabaseSource;
import com.farhad.models.Account;
import com.farhad.models.Customer;
import com.farhad.models.Transaction;
import com.farhad.utils.TableViewGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

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
        TableViewGenerator.constructIncomeAndOutcomeTableViews(incomes, outcomes);
        incomes.setItems(customer.getAccounts().get(0).getIncomes());
        outcomes.setItems(customer.getAccounts().get(0).getOutcomes());
    }

}
