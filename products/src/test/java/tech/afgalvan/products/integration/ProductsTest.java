package tech.afgalvan.products.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import tech.afgalvan.products.controllers.requests.CreateProductRequest;
import tech.afgalvan.products.controllers.responses.ProductResponse;
import tech.afgalvan.products.integration.utils.HttpUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MicronautTest
class ProductsTest {
    private static final String[] products = {"shampoo", "pencil", "soap", "paper"};
    @Inject
    @Client("/api")
    HttpClient client;
    @Inject
    ObjectMapper mapper;

    private static CreateProductRequest productOf(String name) {
        return new CreateProductRequest(name, "https://github.com", 0, "lit", 10);
    }

    public Stream<ProductResponse> getAllProducts() {
        @SuppressWarnings("unchecked")
        Class<List<ProductResponse>> cls = (Class<List<ProductResponse>>) (Object) List.class;
        return HttpUtils.GET("/products", ProductResponse.class, cls)
                .stream();
    }

    public static Stream<Arguments> generateRequests() {
        return Stream.of(products)
                .map(ProductsTest::productOf)
                .map(Arguments::of);
    }

    @BeforeEach
    public void setUp() {
        HttpUtils.setClient(client);
    }

    @ParameterizedTest
    @MethodSource("generateRequests")
    @Order(1)
    void whenISendAPOSTRequestToTheProductsEndpoint_thenTheProductShouldBeSaved(CreateProductRequest request) {
        HttpResponse<ProductResponse> response = HttpUtils.POST("/products", request, ProductResponse.class);
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
    }

    @Test
    @Order(2)
    void whenISendAGETRequestToTheProductsEndpoint_thenAllRegisteredProductsShouldBeRetrieved() {
        List<String> response = getAllProducts()
                .map(ProductResponse::getName)
                .toList();
        assertIterableEquals(Arrays.asList(products), response);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    @Order(3)
    void whenITryToFindAProductById_thenAllRegisteredProductsShouldBeRetrieved(int id) {
        ProductResponse response = HttpUtils.GET("/products/" + id, ProductResponse.class);
        assertEquals(products[id - 1], response.getName());
    }
}
