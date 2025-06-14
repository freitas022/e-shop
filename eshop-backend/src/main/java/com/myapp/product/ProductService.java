package com.myapp.product;

import com.myapp.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;

    public PagedResponse<ProductDto> findAll(Pageable pageable) {
        Page<ProductDto> page = productRepository
                .findAll(pageable)
                .map(ProductDto::new);

        return new PagedResponse<>(page.getContent(), page);
    }

	public ProductDto findById(Long id) {
		return productRepository.findById(id).map(ProductDto::new)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

    public PagedResponse<ProductDto> searchProduct(String name, Pageable pageable) {
        Page<ProductDto> page = productRepository.findByNameContainingIgnoreCase(name, pageable);
        return new PagedResponse<>(page.getContent(), page);
    }
}
