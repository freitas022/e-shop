package com.meuportifolio.eshop.services;

import com.meuportifolio.eshop.dto.ProductDto;
import com.meuportifolio.eshop.repositories.ProductRepository;
import com.meuportifolio.eshop.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<ProductDto> findAll() {
		LOGGER.info("product - find all");
		return productRepository.findAll()
				.stream()
				.map(ProductDto::new)
				.toList();
	}

	public ProductDto findById(Long id) {
		LOGGER.info("product - find by id");
		return productRepository.findById(id)
				.map(ProductDto::new)
				.orElseThrow(ResourceNotFoundException::new);
	}

	public List<ProductDto> findByName(String name) {
		LOGGER.info("product - find by name()");
		return productRepository.findByNameContainingIgnoreCase(name)
				.stream()
				.map(ProductDto::new)
				.toList();
	}
}
