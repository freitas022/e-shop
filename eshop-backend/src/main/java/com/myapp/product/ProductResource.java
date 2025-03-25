package com.myapp.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<List<ProductDto>> findAll(@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
													@RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
													@RequestParam(value = "orderBy", defaultValue = "price") String orderBy,
													@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		return ResponseEntity.ok().body(productService.findAll(pageNumber, pageSize, orderBy, direction));
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
