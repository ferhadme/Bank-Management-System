module com.farhad {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.farhad.controllers;
    opens com.farhad.controllers to javafx.fxml;
    exports com.farhad.application;
    opens com.farhad.application to javafx.fxml;
}