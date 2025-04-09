package com.example.productcatalog.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductUpdateDTO {

    @Size(min = 1, message = "Name cannot be blank")
    private String name;

    @Size(min = 1, message = "Description cannot be blank")
    private String description;

    @Positive(message = "Price must be greater than zero")
    private Double price;

    // Getters and Setters
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
