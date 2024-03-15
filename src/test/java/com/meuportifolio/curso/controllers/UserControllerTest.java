package com.meuportifolio.curso.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meuportifolio.curso.entities.User;
import com.meuportifolio.curso.services.UserService;

@ActiveProfiles("test")
@WebMvcTest(UserController.class)
class UserControllerTest {

	private static final String BASE_URL = "/users";
	private static final String BASE_URL_ID = "/users/{id}";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	private User user;

	private static final Long USER_ID = System.currentTimeMillis();

	@BeforeEach
	void setUp() {
		user = new User(USER_ID, "Matheus Freitas", "math3612@mail.com", "99999-8888", "*11118*");
	}

	@Test
	void testFindAll() throws Exception {
		// Arrange
		List<User> list = new ArrayList<>();
		list.add(user);
		given(userService.findAll()).willReturn(list);

		// Act
		ResultActions response = mockMvc.perform(get(BASE_URL));

		// Assert
		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.[0].id").value(user.getId()))
				.andExpect(jsonPath("$.[0].name").value(user.getName()))
				.andExpect(jsonPath("$.[0].email").value(user.getEmail()))
				.andExpect(jsonPath("$.[0].phone").value(user.getPhone()))
				.andExpect(jsonPath("$.[0].password").value(user.getPassword()));
	}

	@Test
	void testFindById() throws Exception {
		// Arrange
		Long newUserId = System.currentTimeMillis();
		user.setId(newUserId);
		given(userService.findById(anyLong())).willReturn(user);

		// Act
		ResultActions result = mockMvc.perform(get(BASE_URL_ID, newUserId).contentType(MediaType.APPLICATION_JSON));

		// Assert
		result.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(newUserId))
				.andExpect(jsonPath("$.name").value(user.getName()))
				.andExpect(jsonPath("$.email").value(user.getEmail()))
				.andExpect(jsonPath("$.phone").value(user.getPhone()))
				.andExpect(jsonPath("$.password").value(user.getPassword()));

	}

	@Test
	void testInsert() throws Exception {
		// Arrange
		given(userService.insert(any(User.class))).willReturn(user);

		// Act
		ResultActions result = mockMvc.perform(
				post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)));

		// Assert
		result.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(USER_ID))
				.andExpect(jsonPath("$.name").value(user.getName()))
				.andExpect(jsonPath("$.email").value(user.getEmail()))
				.andExpect(jsonPath("$.phone").value(user.getPhone()))
				.andExpect(jsonPath("$.password").value(user.getPassword()));
	}

	@Test
	void testUpdate() throws Exception {
		// Arrange
		User userRef = new User(USER_ID, "Luan Ferreira", "luan@mail.com", "92233-3231", "*hgbs879s123#$");
		given(userService.update(USER_ID, userRef)).willReturn(userRef);

		// Act
		ResultActions result = mockMvc.perform(put(BASE_URL_ID, USER_ID).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userRef)));

		// Assert
		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(USER_ID)).andExpect(jsonPath("$.name").value(userRef.getName()))
				.andExpect(jsonPath("$.email").value(userRef.getEmail()))
				.andExpect(jsonPath("$.phone").value(userRef.getPhone()))
				.andExpect(jsonPath("$.password").value(userRef.getPassword()));
	}
}
