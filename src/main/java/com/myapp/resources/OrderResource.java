package com.myapp.resources;

import com.myapp.dtos.OrderDto;
import com.myapp.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

	private final OrderService orderService;
	
	@GetMapping
	public ResponseEntity<List<OrderDto>> findAll() {
		return ResponseEntity.ok().body(orderService.findAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(orderService.findById(id));
	}
}
