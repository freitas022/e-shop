package com.meuportifolio.eshop.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoryControllerITests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return a list of categories.")
    void testFindAll() throws Exception {
        // Arrange
        ResultActions result = mockMvc
                .perform(get("/categories")
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.[0].name").value("Electronics"));
        result.andExpect(jsonPath("$.[1].name").value("Books"));
        result.andExpect(jsonPath("$.[2].name").value("Home & Decor"));
    }

    @Test
    @DisplayName("Should return a single category by id.")
    void testFindById() throws Exception {
        // Arrange
        ResultActions result = mockMvc
                .perform(get("/categories/" + 1L)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").value("Electronics"));
    }
}
