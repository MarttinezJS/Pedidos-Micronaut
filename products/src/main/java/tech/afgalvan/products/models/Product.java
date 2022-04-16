package tech.afgalvan.products.models;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedEntity
public class Product {
    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private Integer id;
    private final String name;
    private final String imageUri;
    private final Double price;
    private final Integer stock;
    private String description;
    private final LocalDateTime creationDateTime = LocalDateTime.now(Clock.systemUTC());
    private LocalDateTime updateDateTime;

    public Product(String name, String imageUri, Integer stock, Double price) {
        this.name = name;
        this.imageUri = imageUri;
        this.stock = stock;
        this.price = price;
    }

    public Product(Integer id, String name, String imageUri, Integer stock, Double price) {
        this.id = id;
        this.name = name;
        this.imageUri = imageUri;
        this.stock = stock;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStock() {
        return stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return getId().equals(product.getId()) && Objects.equals(getName(), product.getName()) && Objects.equals(getPrice(), product.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getImageUri(), getPrice());
    }
}
