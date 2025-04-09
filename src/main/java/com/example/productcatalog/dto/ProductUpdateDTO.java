package com.example.productcatalog.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;

public class ProductUpdateDTO {

    @Size(min = 1, max = 50, message = "Name cannot be blank")
    private String name;

    @Size(min = 1, max = 200, message = "Description cannot be blank")
    private String description;

    @Positive(message = "Price must be greater than zero")
    @DecimalMax("99999.99")
    @Digits(integer = 5, fraction = 2)
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
