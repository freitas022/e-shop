package com.meuportifolio.curso.integrationTests.controllers;

import com.meuportifolio.curso.entities.Order;
import com.meuportifolio.curso.entities.User;
import com.meuportifolio.curso.entities.enums.OrderStatus;
import com.meuportifolio.curso.repositories.OrderRepository;
import com.meuportifolio.curso.repositories.UserRepository;
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

import java.time.Instant;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OrderControllerITest {

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
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach(){
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @AfterEach
    void afterEach() {
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return a list of orders.")
    void testFindAll() {
        User user = userRepository.save(mockUser());
        // Arrange
        List<Order> orders = List.of(
                new Order(null,
                        Instant.now(),
                        OrderStatus.WAITING_PAYMENT,
                        user)
        );

        orderRepository.saveAll(orders);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/orders")
                .then()
                .statusCode(200)
                .body("", hasSize(1));
    }

    @Test
    @DisplayName("Should return a single order by id.")
    void testFindById() {
        // Arrange
        User user = userRepository.save(mockUser());
        Order order = new Order(null,
                                Instant.now(),
                                OrderStatus.WAITING_PAYMENT,
                                user);
        orderRepository.save(order);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/orders/" + order.getId())
                .then()
                .statusCode(200);
    }

    private User mockUser() {
        return new User(null,
                "Math",
                "math@mail.com",
                "2291313-3131",
                "123456");
    }
}
