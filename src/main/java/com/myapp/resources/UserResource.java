package com.myapp.resources;

import com.myapp.dtos.OrderDto;
import com.myapp.dtos.ProductDto;
import com.myapp.dtos.UserDto;
import com.myapp.entities.User;
import com.myapp.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserResource {

	private final UserService userService;

	@Operation(summary = "Get all users")
	@ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
			schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserDto.class))}
	)
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<UserDto>> findAll() {
		return ResponseEntity.ok().body(userService.findAll());
	}

	@Operation(summary = "Get user by id")
	@ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
			schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserDto.class))}
	)
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(userService.findById(id));
	}

	@Operation(summary = "Get all orders from a user")
	@ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
			schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = OrderDto.class))}
	)
	@GetMapping(value = "/{id}/orders")
	public ResponseEntity<List<OrderDto>> findOrdersByUserId(@PathVariable Long id) {
		var ordersByUser = userService.findOrdersByUserId(id);
		if (ordersByUser.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok().body(userService.findOrdersByUserId(id));
	}

	@Operation(summary = "Create new user")
	@ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
			schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserDto.class))}
	)
	@PostMapping
	public ResponseEntity<UserDto> insert (@RequestBody User obj) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(userService.insert(obj));
	}

	@Operation(summary = "Delete user by id")
	@ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
			schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserDto.class))}
	)
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Update all fields from a user")
	@ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
			schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserDto.class))}
	)
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto obj) {
		return ResponseEntity.ok().body(userService.update(id, obj));
	}
}
