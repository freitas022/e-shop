package com.meuportifolio.curso.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.meuportifolio.curso.entities.Order;
import com.meuportifolio.curso.entities.enums.OrderStatus;
import com.meuportifolio.curso.services.OrderService;

@ActiveProfiles("test")
@WebMvcTest(OrderController.class)
class OrderControllerTest {

	private static final String BASE_URL = "/orders";
	private static final String BASE_URL_ID = "/orders/{id}";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService service;

	@Test
	void testFindAll() throws Exception {
		// Arrange
		final Order expected = new Order(111L, Instant.parse("2021-03-25T21:33:08Z"), OrderStatus.DELIVERED, null);
		List<Order> list = new ArrayList<>();
		list.add(expected);

		given(service.findAll()).willReturn(list);

		// Act
		ResultActions response = this.mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON));
		// Assert
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].id").value(111L));
	}

	@Test
	void findById() throws Exception {
		// Arrange
		long orderId = System.currentTimeMillis();
		final Order expected = new Order(orderId, Instant.parse("2021-03-25T19:23:56Z"), OrderStatus.DELIVERED, null);

		given(service.findById(orderId)).willReturn(expected);

		// Act
		ResultActions response = this.mockMvc
				.perform(get(BASE_URL_ID, orderId).contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.id").value(orderId))
				.andExpect(jsonPath("$.orderStatus").value("DELIVERED"));
	}
}
