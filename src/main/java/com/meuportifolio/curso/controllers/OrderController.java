package com.meuportifolio.curso.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meuportifolio.curso.entities.Order;
import com.meuportifolio.curso.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
	public ResponseEntity<List<Order>> findAll() {
		List<Order> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@Operation(summary = "Should return only one order.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success."),
			@ApiResponse(responseCode = "204", description = "Should return order not found exception."),
			@ApiResponse(responseCode = "400", description = "Should return bad request.")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id) {
		Order obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}
