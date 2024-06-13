package com.meuportifolio.curso.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.meuportifolio.curso.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.meuportifolio.curso.entities.Product;
import com.meuportifolio.curso.services.ProductService;

@ActiveProfiles("test")
@WebMvcTest(ProductController.class)
class ProductControllerTest {

	private static final String BASE_URL = "/products";
	private static final String BASE_URL_ID = "/products/{id}";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService service;

	@Test
	void testFindAll() throws Exception {
		// Arrange
		Product expected = new Product(2011L, "English Course", "Lorem ipsum.", BigDecimal.TEN, null);
		List<Product> list = new ArrayList<>();
		list.add(expected);

		given(service.findAll()).willReturn(list);

		// Act
		ResultActions response = this.mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].id").value(expected.getId()))
				.andExpect(jsonPath("$[0].name").value(expected.getName()));
	}

	@Test
	void testFindById() throws Exception {
		// Arrange
		Long productId = System.currentTimeMillis();
		Product expected = new Product(productId, "English Course", "Lorem ipsum.", BigDecimal.TEN, null);

		given(service.findById(productId)).willReturn(expected);

		// Act
		ResultActions response = this.mockMvc
				.perform(get(BASE_URL_ID, productId).contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.id").value(expected.getId()))
				.andExpect(jsonPath("$.name").value(expected.getName()))
				.andExpect(jsonPath("$.price").value(expected.getPrice()));
	}

	@Test
	void testFindByName_ExactMatch() throws Exception {
		// Arrange
		String param = "English";
		Product expected = new Product(1L, "English Course", "Lorem ipsum.", BigDecimal.TEN, "");

		given(service.findByName(param)).willReturn(List.of(new ProductDto(expected)));

		// Act
		ResultActions response = this.mockMvc
				.perform(get(BASE_URL + "/search?name=" + param)
						.contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$[0].name").value(expected.getName()))
				.andExpect(jsonPath("$[0].price").value(expected.getPrice()));
	}
}
