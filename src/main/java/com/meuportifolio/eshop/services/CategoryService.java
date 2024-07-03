package com.meuportifolio.eshop.services;

import com.meuportifolio.eshop.dto.CategoryDto;
import com.meuportifolio.eshop.repositories.CategoryRepository;
import com.meuportifolio.eshop.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<CategoryDto> findAll() {
		LOGGER.info("category - find all");
		return categoryRepository.findAll()
				.stream()
				.map(CategoryDto::new)
				.toList();
	}

	public CategoryDto findById(Long id) {
		LOGGER.info( "category - find by id.");
		return categoryRepository.findById(id)
				.map(CategoryDto::new)
				.orElseThrow(ResourceNotFoundException::new);
	}
}
