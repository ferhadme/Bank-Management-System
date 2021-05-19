package com.farhad.controllers;

import com.farhad.App;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebViewController {
    @FXML
    private WebView webView;

    public void loadURL(String url) {
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url);
    }
}
