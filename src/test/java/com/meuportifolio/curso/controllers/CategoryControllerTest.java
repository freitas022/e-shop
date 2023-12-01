package com.meuportifolio.curso.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.meuportifolio.curso.entities.Category;
import com.meuportifolio.curso.services.CategoryService;

@ActiveProfiles("test")
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

	private static final String BASE_URL = "/categories";
	private static final String BASE_URL_ID = "/categories/{id}";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService service;

	@Test
	void findAll() throws Exception {
		// Arrange
		Category expected = new Category(1L, "Smartphones");
		List<Category> list = new ArrayList<>();
		list.add(expected);

		given(service.findAll()).willReturn(list);

		// Act
		ResultActions response = this.mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].name").value("Smartphones"));

	}

	@Test
	void findById() throws Exception {
		// Arrange
		long categoryId = System.currentTimeMillis();
		Category expected = new Category(categoryId, "Smartphones");

		given(service.findById(categoryId)).willReturn(expected);

		// Act
		ResultActions response = this.mockMvc
				.perform(get(BASE_URL_ID, categoryId).contentType(MediaType.APPLICATION_JSON));

		// Assert
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.id").value(categoryId))
				.andExpect(jsonPath("$.name").value("Smartphones"));
	}

}
