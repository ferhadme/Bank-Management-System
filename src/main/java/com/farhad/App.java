package com.farhad;

import com.farhad.database.DatabaseSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;

    @Override
    public void init() throws Exception {
        DatabaseSource.getInstance().open();
    }

    @Override
    public void start(Stage stage) throws IOException {
        modifyWidthAndHeight(stage);
        scene = new Scene(loadFXML("view/dashboard_overview"), 640, 480);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setRoot(Parent root) throws IOException {
        scene.setRoot(root);
    }

    public static void changeStageTitle(String title) {
        ((Stage) scene.getWindow()).setTitle(title);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        DatabaseSource.getInstance().close();
    }

    private void modifyWidthAndHeight(Stage stage) {
        stage.setMinHeight(800);
        stage.setMinWidth(800);
    }
}