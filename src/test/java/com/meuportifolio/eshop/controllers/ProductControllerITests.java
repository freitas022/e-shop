package com.meuportifolio.eshop.controllers;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerITests {

    @Autowired
    MockMvc mockMvc;

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = System.currentTimeMillis();
    }

    @Test
    @DisplayName("Should return a list of products.")
    void testFindAll() throws Exception {

        mockMvc
                .perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].description").value("Powerful smartphone with advanced features"))
                .andExpect(jsonPath("$.[1].description").value("High-performance laptop for professional use"));
    }

    @Test
    @DisplayName("Should return a single product by id.")
    void testFindById() throws Exception {

        mockMvc
                .perform(get("/products/" + existingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Powerful smartphone with advanced features"))
                .andExpect(jsonPath("$.price").value(799.99));
    }

    @Test
    @DisplayName("Throws ResourceNotFoundException")
    void testFindByIdNotFound() throws Exception {

        mockMvc
                .perform(get("/products/" + nonExistingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return a list of products containing the name passed as an argument.")
    void testFindByNameContainingIgnoreCase() throws Exception {
        String name = "Smart";
        mockMvc
                .perform(get("/products/search")
                        .param("name", name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Smartphone X"))
                .andExpect(jsonPath("$.[1].name").value("Smart TV A"));
    }
}
