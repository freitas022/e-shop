package com.meuportifolio.curso.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meuportifolio.curso.entities.Category;
import com.meuportifolio.curso.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/categories")
@Tag(name = "Category", description = "Endpoints for managing categories.")
public class CategoryController {

	private final CategoryService service;

	public CategoryController(CategoryService categoryService) {
		this.service = categoryService;
	}
	
	@Operation(summary = "Should return the list of categories")
	@ApiResponse(responseCode = "200", description = "Should return the list of categories found if present or empty list.")
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		
		List<Category> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@Operation(summary = "Should return only one category.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success."),
			@ApiResponse(responseCode = "204", description = "Should return category not found exception."),
			@ApiResponse(responseCode = "400", description = "Should return bad request.")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		Category obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}
