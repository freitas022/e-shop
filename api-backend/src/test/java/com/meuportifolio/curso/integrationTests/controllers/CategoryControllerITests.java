package com.meuportifolio.curso.integrationTests.controllers;

import com.meuportifolio.curso.entities.Category;
import com.meuportifolio.curso.repositories.CategoryRepository;
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

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CategoryControllerITests {

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
    private CategoryRepository categoryRepository;

    @BeforeEach
    void beforeEach(){
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @AfterEach
    void afterEach(){
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return a list of categories.")
    void testFindAll(){
        // Arrange
        List<Category> categories = List.of(
                new Category(null, "Category 1"),
                new Category(null, "Category 2")
        );

        categoryRepository.saveAll(categories);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/categories")
                .then()
                .statusCode(200)
                .body("", hasSize(2));
    }

    @Test
    @DisplayName("Should return a single category by id.")
    void testFindById(){
        // Arrange
        Category category = new Category(null, "Category 1");
        categoryRepository.save(category);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/categories/" + category.getId())
                .then()
                .statusCode(200);
    }
}
