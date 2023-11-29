package com.meuportifolio.curso.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.meuportifolio.curso.entities.Product;
import com.meuportifolio.curso.repositories.ProductRepository;
import com.meuportifolio.curso.services.exceptions.ResourceNotFoundException;

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
}
