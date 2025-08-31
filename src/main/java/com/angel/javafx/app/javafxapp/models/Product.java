package com.angel.javafx.app.javafxapp.models;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {

    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final LongProperty price = new SimpleLongProperty();

    public Product() {
    }

    public Product(String name, String description, Long price) {
        this.name.set(name);
        this.description.set(description);
        this.price.set(price);
    }

    public Long getId() {
        return id.get() == 0 ? null : id.get();
    }

    public void setId(Long id) {
        this.id.set(id == null ? 0 : id);
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public long getPrice() {
        return price.get();
    }

    public void setPrice(long price) {
        this.price.set(price);
    }

    public LongProperty priceProperty() {
        return price;
    }
}
