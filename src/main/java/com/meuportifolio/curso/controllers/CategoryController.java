package com.meuportifolio.curso.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meuportifolio.curso.entities.Category;
import com.meuportifolio.curso.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	private final CategoryService service;

	public CategoryController(CategoryService categoryService) {
		this.service = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		
		List<Category> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		Category obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}
