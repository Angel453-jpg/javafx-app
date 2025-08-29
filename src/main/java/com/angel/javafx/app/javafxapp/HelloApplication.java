package com.angel.javafx.app.javafxapp;

import com.angel.javafx.app.javafxapp.models.Product;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private final ObservableList<Product> products = FXCollections.observableArrayList(
            new Product("Laptop", "Alguna descripción", 1000L),
            new Product("Mouse", "Alguna descripción del mouse", 500L),
            new Product("CPU", "Alguna descripción del CPU", 800L),
            new Product("Memoria Ram", "Alguna monitor legion", 1500L)
    );

    @Override
    public void start(Stage stage) {
        TableView<Product> tableView = new TableView<>();

        TableColumn<Product, String> nameColumn = new TableColumn<>("Nombre");
        TableColumn<Product, String> descColumn = new TableColumn<>("Descripción");
        TableColumn<Product, String> priceColumn = new TableColumn<>("Precio");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Void> deleteColumn = new TableColumn<>("Eliminar");
        deleteColumn.setCellFactory(param -> new TableCell<>() {

            private final Button deleteButton = new Button("Eliminar");

            {
                deleteButton.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    tableView.getItems().remove(product);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        tableView.getColumns().addAll(nameColumn, descColumn, priceColumn, deleteColumn);
        tableView.setItems(this.products);

        VBox vBox = new VBox(tableView);
        Scene scene = new Scene(vBox, 640, 480);
        stage.setTitle("Gestion de productos!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}