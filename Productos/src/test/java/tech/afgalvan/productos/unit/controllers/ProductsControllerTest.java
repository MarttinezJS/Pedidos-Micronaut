package tech.afgalvan.productos.unit.controllers;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import tech.afgalvan.productos.models.Product;
import tech.afgalvan.productos.services.ProductsService;
import tech.afgalvan.productos.unit.stubs.ProductStub;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class ProductsControllerTest {
    @Inject
    ProductsService productsService;

    @Inject
    @Client("/api/products")
    HttpClient client;

    @Test
    void testProductPostRequest() {
        when(productsService.saveProduct(any(Product.class))).then(invocation -> ProductStub.getStoredProductAnswer());
        HttpRequest<?> request = HttpRequest.POST("/", ProductStub.createProductRequest());
        HttpResponse<?> response = client.toBlocking().exchange(request);
        assertEquals(HttpStatus.CREATED, response.getStatus());

        request = HttpRequest.POST("/", ProductStub.createProductRequest());
        response = client.toBlocking().exchange(request);
        assertEquals(HttpStatus.CREATED, response.getStatus());
    }

    @Test
    void testProductGetRequest() {
        when(productsService.getProducts()).then(invocation -> ProductStub.getProductsAnswer());

        HttpRequest<?> request = HttpRequest.GET("/");
        List<Product> response = client
                .toBlocking()
                .retrieve(request, Argument.of(List.class, Product.class));
        assertIterableEquals(ProductStub.getProductsAnswer(), response);
    }

    @MockBean(ProductsService.class)
    ProductsService productsService() {
        return mock(ProductsService.class);
    }
}
