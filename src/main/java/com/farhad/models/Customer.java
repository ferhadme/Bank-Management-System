package com.farhad.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Customer {
    private StringProperty name;
    private StringProperty phoneNumber;
    private StringProperty email;
    private StringProperty login;
    private StringProperty password;
    private StringProperty otherDetails;
    private ObservableList<Account> accounts;

    public Customer(String name, String phoneNumber, String email, String login,
                    String password, String otherDetails, List<Account> accounts) {
        this.name = new SimpleStringProperty(name);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
        this.otherDetails = new SimpleStringProperty(otherDetails);
        this.accounts = FXCollections.observableArrayList(accounts);
    }


    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getLogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
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

    public ObservableList<Account> getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name=" + name +
                ", phoneNumber=" + phoneNumber +
                ", email=" + email +
                ", login=" + login +
                ", password=" + password +
                ", otherDetails=" + otherDetails +
                ", accounts=" + accounts +
                '}';
    }
}
