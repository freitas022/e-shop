package com.meuportifolio.curso.integrationTests.controllers;

import com.meuportifolio.curso.entities.User;
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

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerITests {

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
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach(){
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @AfterEach
    void afterEach(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return a list of users.")
    void testFindAll(){
        // Arrange
        List<User> users = List.of(
                mockUser()
        );

        userRepository.saveAll(users);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("", hasSize(1));
    }

    @Test
    @DisplayName("Should return a single user by id.")
    void testFindById(){
        // Arrange
        User user = mockUser();
        userRepository.save(user);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users/" + user.getId())
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Should insert on db a user.")
    void testInsert() {
        User user = mockUser();

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Should update a existing user by id.")
    void testUpdate() {
        User updatedUser = new User(null, "Lea", "lea@mail.com", "2293333-1211", "newpassword");

        User existingUser = userRepository.save(mockUser());

        given()
                .contentType(ContentType.JSON)
                .body(updatedUser)
                .when()
                .put("/users/" + existingUser.getId())
                .then()
                .statusCode(200);

        Optional<User> updatedUserFromDB = userRepository.findById(existingUser.getId());
        Assertions.assertNotNull(updatedUserFromDB);
        Assertions.assertEquals(updatedUser.getName(), "Lea");
        Assertions.assertEquals(updatedUser.getEmail(), "lea@mail.com");
        Assertions.assertEquals(updatedUser.getPhone(), "2293333-1211");
        Assertions.assertEquals(updatedUser.getPassword(), "newpassword");
    }


    private User mockUser() {
        return new User(null,
                "Math",
                "math@mail.com",
                "2291313-3131",
                "123456");
    }
}
