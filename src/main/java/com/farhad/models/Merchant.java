package com.farhad.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Merchant {
    private StringProperty merchantName;
    private StringProperty merchantPhone;
    private StringProperty merchantEmail;
    private StringProperty otherDetails;
    private ObservableList<Product> products;

    public Merchant(String merchantName, String merchantPhone, String merchantEmail, String otherDetails,
                    List<Product> products) {
        this.merchantName = new SimpleStringProperty(merchantName);
        this.merchantPhone = new SimpleStringProperty(merchantPhone);
        this.merchantEmail = new SimpleStringProperty(merchantEmail);
        this.otherDetails = new SimpleStringProperty(otherDetails);
        this.products = FXCollections.observableArrayList(products);
    }


    public String getMerchantName() {
        return merchantName.get();
    }

    public StringProperty merchantNameProperty() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName.set(merchantName);
    }

    public String getMerchantPhone() {
        return merchantPhone.get();
    }

    public StringProperty merchantPhoneProperty() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone.set(merchantPhone);
    }

    public String getMerchantEmail() {
        return merchantEmail.get();
    }

    public StringProperty merchantEmailProperty() {
        return merchantEmail;
    }

    public void setMerchantEmail(String merchantEmail) {
        this.merchantEmail.set(merchantEmail);
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

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> products) {
        this.products = products;
    }
}
