package com.meuportifolio.curso.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meuportifolio.curso.entities.Product;
import com.meuportifolio.curso.repositories.ProductRepository;
import com.meuportifolio.curso.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	public Product findById(Long id) {
		return repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException(id));
	}
}
