package com.meuportifolio.eshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meuportifolio.eshop.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerITests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = System.currentTimeMillis();
    }

    @Test
    @DisplayName("Should return a list of users.")
    void testFindAll() throws Exception {

        mockMvc
                .perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Admin"))
                .andExpect(jsonPath("$.[0].email").value("admin@gmail.com"));
    }

    @Test
    @DisplayName("Should return a list of users.")
    void testInsert() throws Exception {

        var user = new User(null, "Test XXX", "test@gmail.com", "911112222", "12345678");
        var jsonBody = objectMapper.writeValueAsString(user);

        mockMvc
                .perform(post("/users")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return a single product by id.")
    void testFindById() throws Exception {

        mockMvc
                .perform(get("/users/" + existingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.password").value("password"));
    }

    @Test
    @DisplayName("Throws ResourceNotFoundException")
    void testFindByIdNotFound() throws Exception {

        mockMvc
                .perform(get("/users/" + nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}