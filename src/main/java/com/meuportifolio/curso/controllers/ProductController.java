package com.meuportifolio.curso.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meuportifolio.curso.entities.Product;
import com.meuportifolio.curso.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/products")
@Tag(name = "Product", description = "Endpoints for managing products.")
public class ProductController {

	private final ProductService service;

	public ProductController(ProductService productService) {
		this.service = productService;
	}

	@Operation(summary = "Should return the list of products")
	@ApiResponse(responseCode = "200", description = "Should return the list of products found if present or empty list.")
	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		List<Product> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@Operation(summary = "Should return only one product.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success."),
			@ApiResponse(responseCode = "204", description = "Should return product not found exception."),
			@ApiResponse(responseCode = "400", description = "Should return bad request.")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Product obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}
