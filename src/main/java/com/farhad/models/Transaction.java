package com.farhad.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction {
    private StringProperty accountId;
    private StringProperty otherDetails;

    public Transaction(String accountId, String otherDetails) {
        this.accountId = new SimpleStringProperty(accountId);
        this.otherDetails = new SimpleStringProperty(otherDetails);
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

    public String getOtherDetails() {
        return otherDetails.get();
    }

    public StringProperty otherDetailsProperty() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails.set(otherDetails);
    }
}
