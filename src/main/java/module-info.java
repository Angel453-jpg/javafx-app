module com.angel.javafx.app.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.angel.javafx.app.javafxapp to javafx.fxml;
    exports com.angel.javafx.app.javafxapp;
}