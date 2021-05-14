module com.farhad {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires org.kordamp.bootstrapfx.core;

    exports com.farhad.controllers;
    opens com.farhad.controllers to javafx.fxml;
    exports com.farhad;
    opens com.farhad to javafx.fxml;
}
