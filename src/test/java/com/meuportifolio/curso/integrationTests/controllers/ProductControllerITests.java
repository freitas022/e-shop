package com.meuportifolio.curso.integrationTests.controllers;

import com.meuportifolio.curso.entities.Product;
import com.meuportifolio.curso.repositories.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ProductControllerITests {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void setUp(){
        postgres.start();
    }

    @AfterAll
    static void afterAll(){
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void beforeEach(){
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @AfterEach
    void afterEach() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return a list of products.")
    void testFindAll() {
        // Arrange
        List<Product> products = List.of(
                new Product(null, "Product FindAll 1", "null", BigDecimal.ONE, ""),
                new Product(null, "Product FindAll 2", "null", BigDecimal.ONE, "")
        );

        productRepository.saveAll(products);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("", hasSize(2));
    }

    @Test
    @DisplayName("Should return a single product by id.")
    void testFindById() {
        // Arrange
        Product product = new Product(null, "Product FindAll 1", "null", BigDecimal.ONE, "");
        productRepository.save(product);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/products/" + product.getId())
                .then()
                .statusCode(200);
    }
}
