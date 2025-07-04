package com.myapp.user;

import com.myapp.order.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<List<UserDto>> findAll(@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                 @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
                                                 @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		return ResponseEntity.ok().body(userService.findAll(pageNumber, pageSize, orderBy, direction));
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
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(userService.findOrdersByUserId(id));
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
