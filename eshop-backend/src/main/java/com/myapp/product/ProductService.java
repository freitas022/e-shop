package com.myapp.product;

import com.myapp.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;

	public List<ProductDto> findAll(Integer pageNumber, Integer pageSize, String orderBy, String direction) {
		var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(direction.toUpperCase()), orderBy);
		return productRepository.findAll(pageRequest).stream().map(ProductDto::new).toList();
	}

	public ProductDto findById(Long id) {
		return productRepository.findById(id).map(ProductDto::new)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}
}
