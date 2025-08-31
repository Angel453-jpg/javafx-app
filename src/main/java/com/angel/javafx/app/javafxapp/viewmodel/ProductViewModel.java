package com.angel.javafx.app.javafxapp.viewmodel;

import com.angel.javafx.app.javafxapp.models.Product;
import com.angel.javafx.app.javafxapp.services.ProductService;
import com.angel.javafx.app.javafxapp.services.ProductServiceWebClient;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductViewModel {

    private final ProductService service = new ProductServiceWebClient();

    //Lista observable de productos (Para tableview)
    private final ObservableList<Product> products = FXCollections.observableArrayList();

    //Campos del formulario
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty price = new SimpleStringProperty();
    private final StringProperty actionButtonText = new SimpleStringProperty("Agregar");

    //Estado
    private final ObjectProperty<Product> selectedProduct = new SimpleObjectProperty<>();

    public ProductViewModel() {
        products.addAll(service.findAll());
    }

    // -- Getters y bindings -- //
    public ObservableList<Product> getProducts() {
        return products;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty priceProperty() {
        return price;
    }

    public StringProperty actionButtonTextProperty() {
        return actionButtonText;
    }

    public ObjectProperty<Product> selectedProductProperty() {
        return selectedProduct;
    }

    // -- Lógica de negocio -- //

    public void saveOrUpdate() {
        String n = name.get();
        String d = description.get();
        String p = price.get();

        if (n == null || n.isBlank() || d == null || d.isBlank() || p == null || p.isBlank()) {
            throw new IllegalArgumentException("Por favor complete todos los campos.");
        }

        try {
            long priceValue = Long.parseLong(p);

            if (selectedProduct.get() == null) {
                Product newProduct = new Product(n, d, priceValue);
                Product created = service.save(newProduct);
                products.add(created);
            } else {
                Product product = selectedProduct.get();
                product.setName(n);
                product.setDescription(d);
                product.setPrice(priceValue);
                service.update(product);
                products.set(products.indexOf(product), product); // Refresca la tabla
                selectedProduct.set(null);
            }

            clearForm();

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El precio debe ser un número válido.");
        }

    }

    public void delete(Product product) {
        service.delete(product);
        products.remove(product);
    }

    public void edit(Product product) {
        selectedProduct.set(product);
        name.set(product.getName());
        description.set(product.getDescription());
        price.set(String.valueOf(product.getPrice()));
        actionButtonText.set("Actualizar");
    }

    public void clearForm() {
        selectedProduct.set(null);
        name.set("");
        description.set("");
        price.set("");
        actionButtonText.set("Agregar");
    }

}
