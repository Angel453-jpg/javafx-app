module com.angel.javafx.app.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;


    opens com.angel.javafx.app.javafxapp to javafx.base;
    opens com.angel.javafx.app.javafxapp.models to javafx.base;
    exports com.angel.javafx.app.javafxapp;
}