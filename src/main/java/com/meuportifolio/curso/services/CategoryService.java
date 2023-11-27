package com.meuportifolio.curso.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.meuportifolio.curso.entities.Category;
import com.meuportifolio.curso.repositories.CategoryRepository;
import com.meuportifolio.curso.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	private final CategoryRepository repository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.repository = categoryRepository;
	}

	public List<Category> findAll() {
		return repository.findAll();
	}

	public Category findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}
}
