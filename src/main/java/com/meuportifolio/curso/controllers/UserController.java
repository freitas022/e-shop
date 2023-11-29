package com.meuportifolio.curso.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.meuportifolio.curso.dto.UserDto;
import com.meuportifolio.curso.entities.User;
import com.meuportifolio.curso.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "User", description = "Endpoints for managing users.")
public class UserController {

	private final UserService service;

	public UserController(UserService userService) {
		this.service = userService;
	}

	@Operation(summary = "Should return the list of users found")
	@ApiResponse(responseCode = "200", description = "Should return the list of users found if present or else empty list.")
	@GetMapping
	public ResponseEntity<List<UserDto>> findAll() {
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list.stream().map(UserDto::new).toList());
	}

	@Operation(summary = "Should return only one user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success."),
			@ApiResponse(responseCode = "204", description = "Should return user not found exception."),
			@ApiResponse(responseCode = "400", description = "Bad Request.")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable Long id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDto(obj));
	}

	@Operation(summary = "Should insert a user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User created with success."),
			@ApiResponse(responseCode = "400", description = "Should return bad request.")
	})
	@PostMapping
	public ResponseEntity<UserDto> insert(@RequestBody User obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(new UserDto(obj));
	}

	@Operation(summary = "Should delete one user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User deleted with success."),
			@ApiResponse(responseCode = "204", description = "Should return user not found exception.")
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Should update an existing user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User updated with success."),
			@ApiResponse(responseCode = "204", description = "Should return user not found exception."),
			@ApiResponse(responseCode = "400", description = "Bad Request.")
	})
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody User obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(new UserDto(obj));
	}
}
