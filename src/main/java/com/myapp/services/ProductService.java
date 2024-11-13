package com.myapp.services;

import com.myapp.dtos.ProductDto;
import com.myapp.repositories.ProductRepository;
import com.myapp.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;

	public List<ProductDto> findAll() {
		return productRepository.findAll().stream().map(ProductDto::new).toList();
	}

	public ProductDto findById(Long id) {
		return productRepository.findById(id).map(ProductDto::new)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}
}
