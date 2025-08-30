module com.angel.javafx.app.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires spring.webflux;
    requires reactor.core;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    opens com.angel.javafx.app.javafxapp to javafx.base;
    opens com.angel.javafx.app.javafxapp.models to javafx.base, com.fasterxml.jackson.databind;
    exports com.angel.javafx.app.javafxapp;
}