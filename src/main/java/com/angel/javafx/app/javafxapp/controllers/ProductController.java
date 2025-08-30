package com.angel.javafx.app.javafxapp.controllers;

import com.angel.javafx.app.javafxapp.models.Product;
import com.angel.javafx.app.javafxapp.services.ProductService;
import com.angel.javafx.app.javafxapp.services.ProductServiceWebClient;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductController {

    private final ProductService service = new ProductServiceWebClient();

    private Product productSelected;

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, String> descColumn;

    @FXML
    private TableColumn<Product, Long> priceColumn;

    @FXML
    private TableColumn<Product, Void> editColumn;

    @FXML
    private TableColumn<Product, Void> deleteColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descField;

    @FXML
    private TextField priceField;

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;


    @FXML
    private void initialize() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.setItems(FXCollections.observableArrayList(service.findAll()));
        addButton.setOnAction(e -> handleSaveOrUpdate());
        clearButton.setOnAction(e -> clearForm());

        setupEditColumn();
        setupDeleteColumn();

    }

    private void handleSaveOrUpdate() {
        String name = nameField.getText();
        String description = descField.getText();
        String priceText = priceField.getText();

        if (name.isBlank() || description.isBlank() || priceText.isBlank()) {
            showError("Todos los campos son obligatorios.");
            return;
        }

        try {
            Long price = Long.parseLong(priceText);

            if (productSelected == null) {
                Product newProduct = new Product(name, description, price);
                Product created = service.save(newProduct);
                tableView.getItems().add(created);
            } else {
                productSelected.setName(name);
                productSelected.setDescription(description);
                productSelected.setPrice(price);
                service.update(productSelected);
                tableView.refresh();
                productSelected = null;
                addButton.setText("Agregar");
            }

            clearForm();

        } catch (NumberFormatException ex) {
            showError("El precio debe ser un número válido.");
        }

    }

    private void clearForm() {
        productSelected = null;
        addButton.setText("Agregar");
        nameField.clear();
        descField.clear();
        priceField.clear();
    }

    private void setupEditColumn() {
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Editar");

            {
                editButton.setOnAction(event -> {
                    productSelected = getTableView().getItems().get(getIndex());
                    nameField.setText(productSelected.getName());
                    descField.setText(productSelected.getDescription());
                    priceField.setText(String.valueOf(productSelected.getPrice()));
                    addButton.setText("Editar");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editButton);
            }
        });
    }

    private void setupDeleteColumn() {
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Eliminar");

            {
                deleteButton.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    service.delete(product);
                    tableView.getItems().remove(product);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }

}


