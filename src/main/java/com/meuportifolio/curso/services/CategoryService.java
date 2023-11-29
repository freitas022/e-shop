package com.meuportifolio.curso.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.meuportifolio.curso.entities.Category;
import com.meuportifolio.curso.repositories.CategoryRepository;
import com.meuportifolio.curso.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

	private final CategoryRepository repository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.repository = categoryRepository;
	}

	public List<Category> findAll() {
		LOGGER.info("Searching all categories.");
		return repository.findAll();
	}

	public Category findById(Long id) {
		LOGGER.info( "Searching category by id.");
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}
}
