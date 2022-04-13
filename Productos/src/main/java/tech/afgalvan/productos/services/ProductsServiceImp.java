package tech.afgalvan.productos.services;

import jakarta.inject.Singleton;
import tech.afgalvan.productos.data.ProductsRepository;
import tech.afgalvan.productos.models.Product;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
public class ProductsServiceImp implements ProductsService {

    private final ProductsRepository productsRepository;

    public ProductsServiceImp(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Product saveProduct(Product product) {
        return productsRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return StreamSupport.stream(productsRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
