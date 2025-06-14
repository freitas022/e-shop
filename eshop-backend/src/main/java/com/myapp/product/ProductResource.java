package com.myapp.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<PagedResponse<ProductDto>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok().body(productService.findAll(pageable));
	}

	@Operation(summary = "Get product by id")
	@ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
			schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ProductDto.class))}
	)
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productService.findById(id));
	}

	@Operation(summary = "Search products by name")
	@ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
			schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ProductDto.class))}
	)
	@GetMapping("/search")
	public ResponseEntity<PagedResponse<ProductDto>> searchProducts(
			@RequestParam(value = "name", defaultValue = "") String name,
			@PageableDefault(page = 0, size = 12, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

		return ResponseEntity.ok(productService.searchProduct(name, pageable));
	}
}
