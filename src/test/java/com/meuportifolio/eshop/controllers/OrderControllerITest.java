package com.meuportifolio.eshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meuportifolio.eshop.dto.CustomerDto;
import com.meuportifolio.eshop.dto.OrderDto;
import com.meuportifolio.eshop.entities.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerITest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;
    private OrderDto orderDto;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = System.currentTimeMillis();
        orderDto = new OrderDto(null, Instant.now(), OrderStatus.WAITING_PAYMENT,
                new CustomerDto(1L, "Admin"), null);
    }

    @Test
    @DisplayName("Should return a list of orders.")
    void testFindAll() throws Exception {

        mockMvc
                .perform(get("/orders")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].customer.name").value("Admin"));

    }

    @Test
    @DisplayName("Should return a single order by id.")
    void testFindById() throws Exception {

        mockMvc
                .perform(get("/orders/" + existingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.[0].name").value("Modern Sofa Set"))
                .andExpect(jsonPath("$.items.[0].price").value(1499.99));
    }

    @Test
    @DisplayName("Should return ResourceNotFoundException")
    void testFindByIdNotFound() throws Exception {

        mockMvc
                .perform(get("/orders/" + nonExistingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should insert new order")
    void insert() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(orderDto);

        mockMvc
                .perform(post("/orders")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.total").value(0));
    }

}