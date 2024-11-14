package com.myapp.resources;

import com.myapp.dtos.OrderDto;
import com.myapp.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping
	public ResponseEntity<OrderDto> create(@RequestBody OrderDto orderDto) {
		return ResponseEntity.ok().body(orderService.create(orderDto));
	}
}
