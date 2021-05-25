package com.farhad.controllers;

import com.farhad.App;
import com.farhad.database.DatabaseSource;
import com.farhad.models.Account;
import com.farhad.models.Customer;
import com.farhad.models.Transaction;
import com.farhad.utils.AlertGenerator;
import com.farhad.utils.DialogGenerator;
import com.farhad.utils.TableViewGenerator;
import com.farhad.utils.TableViewToExcel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;

public class AccountsController {
    @FXML
    private ListView<Account> accountsListView;

    @FXML
    private BorderPane root;

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

    private ContextMenu menu;
    private MenuItem delete;

    private Customer customer;

    public void initialize() {
        VBox.setMargin(accountInfoVBox, new Insets(20, 0, 0, 0));
        customer = DatabaseSource.getInstance().getCustomer();
        TableViewGenerator.constructIncomeAndOutcomeTableViews(incomes, outcomes);

        MenuItem delete = new MenuItem("Delete");
        MenuItem update = new MenuItem("Update");
        MenuItem deposit = new MenuItem("Deposit");
        MenuItem withdraw = new MenuItem("Withdraw");
        MenuItem incomesToExcel = new MenuItem("Write incomes to Excel");
        MenuItem outcomesToExcel = new MenuItem("Write outcomes to Excel");
        menu = new ContextMenu();
        menu.getItems().addAll(update, deposit, withdraw, incomesToExcel, outcomesToExcel);
        delete.setOnAction(this::deleteAccount);
        update.setOnAction(this::updateAccount);
        deposit.setOnAction(this::deposit);
        withdraw.setOnAction(this::withdraw);
        incomesToExcel.setOnAction(this::writeIncomesToExcel);
        outcomesToExcel.setOnAction(this::writeOutcomesToExcel);
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
        accountsListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Account> call(ListView<Account> param) {
                ListCell<Account> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(Account item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty | item == null) {
                            setText(null);
                        } else {
                            setText(item.getAccountName());
                        }
                    }
                };

                cell.emptyProperty().addListener(
                        (observable, wasEmpty, nowEmpty) -> {
                            if (nowEmpty) {
                                cell.setContextMenu(null);
                            } else if (accountsListView.getItems().size() == 1) {
                                menu.getItems().remove(delete);
                                cell.setContextMenu(menu);
                            } else {
                                menu.getItems().add(delete);
                                cell.setContextMenu(menu);
                            }
                        }
                );
                return cell;
            }
        });
    }

    private void deleteAccount(ActionEvent event) {
        boolean confirm = AlertGenerator.showConfirmationAlert("Deletion", "Are you sure?",
                "This process cannot be undone");
        if (confirm) {
            Account account = accountsListView.getSelectionModel().getSelectedItem();
            DatabaseSource.getInstance().deleteAccount(account);
            customer.getAccounts().remove(account);
            accountsListView.getSelectionModel().clearSelection();
        }
    }

    private void updateAccount(ActionEvent event) {
        Account account = accountsListView.getSelectionModel().getSelectedItem();
        Dialog<ButtonType> dialog = DialogGenerator.getDialog("Update Account", "dialog_pane");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("view/update_account_dialog.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.APPLY) {
            UpdateAccountDialogController controller = loader.getController();
            boolean successful = controller.updateAccountInformation(account);
            if (successful) {
                AlertGenerator.showInfoAlert("Successful", "Account is updated successfully", "");
            } else {
                AlertGenerator.showErrorAlert("Incorrect Input", "Be sure you're filling right information",
                        "");
                updateAccount(null);
            }
        }
        accountsListView.getSelectionModel().clearSelection();
    }

    private void deposit(ActionEvent event) {
        Account account = accountsListView.getSelectionModel().getSelectedItem();
        Dialog<ButtonType> dialog = DialogGenerator.getDialog("Deposit money", "dialog_pane");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("view/deposit_dialog.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.APPLY) {
            UpdateAccountDialogController controller = loader.getController();
            boolean successful = controller.deposit(account);
            if (successful) {
                AlertGenerator.showInfoAlert("Successful", "Deposit has been made successfully", "");
            } else {
                AlertGenerator.showErrorAlert("Wrong Number Format",
                        "Be sure you're filling right information", "");
            }
        }
        accountsListView.getSelectionModel().clearSelection();
    }

    private void withdraw(ActionEvent event) {
        Account account = accountsListView.getSelectionModel().getSelectedItem();
        Dialog<ButtonType> dialog = DialogGenerator.getDialog("Withdraw money", "dialog_pane");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("view/withdraw_dialog.fxml"));
        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.APPLY) {
            UpdateAccountDialogController controller = loader.getController();
            boolean successful = controller.withdraw(account);
            if (successful) {
                AlertGenerator.showInfoAlert("Successful", "Withdraw has been made successfully", "");
            } else {
                AlertGenerator.showErrorAlert("Wrong Number Format",
                        "Be sure you're filling right information and you have enough money", "");
            }
        }
        accountsListView.getSelectionModel().clearSelection();
    }

    private void writeIncomesToExcel(ActionEvent event) {
        TableViewToExcel.write(incomes, "Incomes");
        accountsListView.getSelectionModel().clearSelection();
    }

    private void writeOutcomesToExcel(ActionEvent event) {
        TableViewToExcel.write(outcomes, "Outcomes");
        accountsListView.getSelectionModel().clearSelection();
    }
}
