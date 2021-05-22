package com.farhad.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public class Account {
    private StringProperty accountId;
    private StringProperty accountName;
    private StringProperty otherAccountDetails;
    private DoubleProperty amountOfMoney;

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
}
