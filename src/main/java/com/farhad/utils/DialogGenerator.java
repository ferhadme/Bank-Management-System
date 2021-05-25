package com.farhad.utils;

import com.farhad.App;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;

public class DialogGenerator {
    public static Dialog<ButtonType> getDialog(String title, String cssFile) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.initModality(Modality.APPLICATION_MODAL);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);
        dialogPane.setMinWidth(Region.USE_PREF_SIZE);
        dialogPane.setPrefHeight(200);
        dialogPane.setPrefWidth(500.0);
        dialogPane.getStylesheets().add(App.class.getResource("style/" + cssFile + ".css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        return dialog;
    }
}
