package com.example.productcatalog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;

public class ProductDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 50)
    private String name;

    @Min(value = 0, message = "Price must be positive")
    @NotNull(message = "Price cannot be null")
    @DecimalMax("99999.99")
    @Digits(integer = 5, fraction = 2)
    private Double price;

    @NotBlank(message = "Description is required")
    @Size(max = 200)
    private String description;

    // Constructor, getters, and setters
    public ProductDTO() {
    }

    public ProductDTO(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public ProductDTO(Long id, String name, Double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
