package com.angel.javafx.app.javafxapp.controllers;

import com.angel.javafx.app.javafxapp.models.Product;
import com.angel.javafx.app.javafxapp.viewmodel.ProductViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductController {

    private final ProductViewModel viewModel = new ProductViewModel();

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, String> descColumn;

    @FXML
    private TableColumn<Product, Number> priceColumn;

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
    public void initialize() {

        //Bindings
        nameField.textProperty().bindBidirectional(viewModel.nameProperty());
        descField.textProperty().bindBidirectional(viewModel.descriptionProperty());
        priceField.textProperty().bindBidirectional(viewModel.priceProperty());
        addButton.textProperty().bind(viewModel.actionButtonTextProperty());

        tableView.setItems(viewModel.getProducts());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Eventos
        addButton.setOnAction(e -> {
            try {
                viewModel.saveOrUpdate();
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });

        clearButton.setOnAction(e -> viewModel.clearForm());

        //Editar
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Editar");

            {
                btn.setOnAction(e -> viewModel.edit(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        //Eliminar
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Eliminar");

            {
                btn.setOnAction(e -> viewModel.delete(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de validación");
        alert.setHeaderText("No se pudo guardar el producto");
        alert.setContentText(msg);
        alert.showAndWait();
    }

}


