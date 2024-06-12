package com.meuportifolio.curso.services;

import com.meuportifolio.curso.dto.ProductDto;
import com.meuportifolio.curso.entities.Product;
import com.meuportifolio.curso.repositories.ProductRepository;
import com.meuportifolio.curso.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	private final ProductRepository repository;

	public ProductService(ProductRepository productRepository) {
		this.repository = productRepository;
	}

	public List<Product> findAll() {
		LOGGER.info("Searching all products.");
		return repository.findAll();
	}

	public Product findById(Long id) {
		LOGGER.info("Searching product by id.");
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public List<ProductDto> findByName(String name) {
		return repository.findByNameContainingIgnoreCase(name)
				.stream()
				.map(ProductDto::new)
				.toList();
	}
}
