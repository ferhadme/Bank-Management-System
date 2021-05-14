package com.farhad;

import com.farhad.security.PBKDF2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
//        Panel panel = new Panel("This is the title");
//        panel.getStyleClass().add("panel-primary");                            //(2)
//        BorderPane content = new BorderPane();
//        content.setPadding(new Insets(20));
//        Button button = new Button("Hello BootstrapFX");
//        button.getStyleClass().setAll("btn","btn-danger");                     //(2)
//        content.setCenter(button);
//        panel.setBody(content);
//
//        Scene scene = new Scene(panel);
//        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());       //(3)
//
//        primaryStage.setTitle("BootstrapFX");
//        primaryStage.setScene(scene);
//        primaryStage.sizeToScene();
//        primaryStage.show();

        try {
            PBKDF2.generateHashPassword("acdajflas;fjdlow2");
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        scene = new Scene(loadFXML("view/primary"), 640, 480);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("style/buttons.css")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}