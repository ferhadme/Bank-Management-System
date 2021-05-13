module com.farhad {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.farhad to javafx.fxml;
    exports com.farhad;
}