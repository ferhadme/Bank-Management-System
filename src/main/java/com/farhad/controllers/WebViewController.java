package com.farhad.controllers;

import com.farhad.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebViewController {
    /*
    this is not actual source code. Just for testing...
     */
    public static final String SOURCE_CODE = "https://github.com/ferhad2207/Data-Structures-and-Algorithms";

    @FXML
    private WebView webView;
    @FXML
    private Button homeButton;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button copyButton;

    private WebEngine webEngine;
    private WebHistory history;
    private int historyCounter;
    private int historyMaxCounter;

    public void initialize() {
        VBox.setVgrow(webView, Priority.ALWAYS);
        webView.setContextMenuEnabled(false);
        Tooltip.install(prevButton, new Tooltip("Click to go back"));
        Tooltip.install(nextButton, new Tooltip("Click to go forward"));
        Tooltip.install(refreshButton, new Tooltip("Reload this page"));
        Tooltip.install(copyButton, new Tooltip("Copy link of the page to clipboard"));
        homeButton.setOnAction(this::homeButtonOnAction);
        prevButton.setOnAction(this::prevButtonOnAction);
        nextButton.setOnAction(this::nextButtonOnAction);
        refreshButton.setOnAction(this::refreshButtonOnAction);
        copyButton.setOnAction(this::copyButtonOnAction);
    }

    /*
    true --> user comes from login page
    false --> user comes from registration page
     */
    private boolean userComesFrom;

    public void loadURL(String url, boolean value) {
        userComesFrom = value;
        Tooltip.install(homeButton, new Tooltip(
                userComesFrom ? "Go to Login page" : "Go to Registration page"
        ));
        webEngine = webView.getEngine();
        history = webEngine.getHistory();
        webEngine.load(url);
    }

    private void homeButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/login.fxml"));
            Parent root = fxmlLoader.load();
            App.setRoot(root);
        } catch (IOException e) {
            Logger.getLogger("IOException").log(Level.SEVERE, "Error has happened in LoginController.java");
        }
    }

    private void prevButtonOnAction(ActionEvent event) {
        try {
            history.go(-1);
        } catch (IndexOutOfBoundsException e) {
            //
        }
    }

    private void nextButtonOnAction(ActionEvent event) {
        try {
            history.go(1);
        } catch (IndexOutOfBoundsException e) {
            //
        }
    }

    private void refreshButtonOnAction(ActionEvent event) {
        webEngine.reload();
    }

    private void copyButtonOnAction(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(webEngine.getLocation());
        clipboard.setContent(content);
    }

}










