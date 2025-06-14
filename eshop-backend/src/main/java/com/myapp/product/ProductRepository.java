package com.myapp.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{

    Page<ProductDto> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
