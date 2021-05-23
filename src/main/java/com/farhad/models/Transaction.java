package com.farhad.models;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction {
    private final String accountId;
    private final String otherDetails;
    private final String destinationAccountId;
    private final float amountOfTransaction;

    public Transaction(String accountId, String destinationAccountId, String otherDetails, float amountOfTransaction) {
        this.accountId = accountId;
        this.destinationAccountId = destinationAccountId;
        this.otherDetails = otherDetails;
        this.amountOfTransaction = amountOfTransaction;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public float getAmountOfTransaction() {
        return amountOfTransaction;
    }
}
