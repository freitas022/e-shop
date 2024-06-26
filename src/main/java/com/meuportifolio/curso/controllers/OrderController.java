package com.meuportifolio.curso.controllers;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.meuportifolio.curso.dto.OrderDto;
import com.meuportifolio.curso.entities.Order;
import com.meuportifolio.curso.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/orders")
@Tag(name = "Order", description = "Endpoints for managing orders.")
public class OrderController {

	private final OrderService service;

	public OrderController(OrderService orderService) {
		this.service = orderService;
	}

	@Operation(summary = "Should return the list of orders")
	@ApiResponse(responseCode = "200", description = "Should return the list of orders found if present or empty list.")
	@GetMapping
	public ResponseEntity<List<OrderDto>> findAll() {
		List<Order> list = service.findAll();
		return ResponseEntity.ok().body(list.stream().map(OrderDto::new).toList());
	}

	@Operation(summary = "Should return only one order.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success."),
			@ApiResponse(responseCode = "204", description = "Should return order not found exception."),
			@ApiResponse(responseCode = "400", description = "Should return bad request.")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderDto> findById(@PathVariable Long id) {
		Order obj = service.findById(id);
		return ResponseEntity.ok().body(new OrderDto(obj));
	}

	@PostMapping
	public ResponseEntity<OrderDto> insert(@Valid @RequestBody OrderDto orderDto) {
		orderDto = service.createOrder(orderDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(orderDto.getId()).toUri();
		return ResponseEntity.created(uri).body(orderDto);
	}
}
