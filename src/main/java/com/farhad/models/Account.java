package com.farhad.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Account {
    private StringProperty accountId;
    private StringProperty accountName;
    private StringProperty otherAccountDetails;
    private DoubleProperty amountOfMoney;
    private ObservableList<Transaction> incomes;
    private ObservableList<Transaction> outcomes;

    public Account(String accountId, String accountName, double amountOfMoney, String otherAccountDetails,
                   List<Transaction> incomes, List<Transaction> outcomes) {
        this.accountId = new SimpleStringProperty(accountId);
        this.accountName = new SimpleStringProperty(accountName);
        this.otherAccountDetails = new SimpleStringProperty(otherAccountDetails);
        this.amountOfMoney = new SimpleDoubleProperty(amountOfMoney);
        this.incomes = FXCollections.observableArrayList(incomes);
        this.outcomes = FXCollections.observableArrayList(outcomes);
    }

    public String getAccountId() {
        return accountId.get();
    }

    public StringProperty accountIdProperty() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId.set(accountId);
    }

    public String getAccountName() {
        return accountName.get();
    }

    public StringProperty accountNameProperty() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName.set(accountName);
    }

    public String getOtherAccountDetails() {
        return otherAccountDetails.get();
    }

    public StringProperty otherAccountDetailsProperty() {
        return otherAccountDetails;
    }

    public void setOtherAccountDetails(String otherAccountDetails) {
        this.otherAccountDetails.set(otherAccountDetails);
    }

    public double getAmountOfMoney() {
        return amountOfMoney.get();
    }

    public DoubleProperty amountOfMoneyProperty() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney.set(amountOfMoney);
    }

    public ObservableList<Transaction> getIncomes() {
        return incomes;
    }

    public ObservableList<Transaction> getOutcomes() {
        return outcomes;
    }

    @Override
    public String toString() {
        return getAccountName();
    }
}
