package com.myapp.resources;

import com.myapp.dtos.ProductDto;
import com.myapp.services.ProductService;
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
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> findAll() {
		return ResponseEntity.ok().body(productService.findAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productService.findById(id));
	}
}
