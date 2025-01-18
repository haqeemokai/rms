package com.example.rms.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "menuItems")
public class MenuItem {
    @Id
    private String id; // Custom ID
    private String name;
    private String description;
    private double price;
    private boolean available;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return '\n' + "MenuItem - " + "id= " + id + '\n' +
                "Name - " + name + '\n' +
                "Description - " + description + '\n' +
                "Price - " + price + '\n' +
                "Available - " + available + '\n';
    }
}
