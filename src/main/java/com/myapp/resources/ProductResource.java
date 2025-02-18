package com.myapp.resources;

import com.myapp.dtos.ProductDto;
import com.myapp.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	private final ProductService productService;

	@Operation(summary = "Get all products")
	@ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
			schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ProductDto.class))}
	)
	@GetMapping
	public ResponseEntity<List<ProductDto>> findAll() {
		return ResponseEntity.ok().body(productService.findAll());
	}

	@Operation(summary = "Get product by id")
	@ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
			schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ProductDto.class))}
	)
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productService.findById(id));
	}
}
