package com.meuportifolio.curso.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.meuportifolio.curso.entities.Product;
import com.meuportifolio.curso.repositories.ProductRepository;
import com.meuportifolio.curso.services.exceptions.ResourceNotFoundException;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private Product product;

	@BeforeEach
	void setUp() {
		product = new Product(19191919L, "Smart TV 55", "Lorem ipsum dollor sit amet", BigDecimal.valueOf(3200.0), "");
	}

	@Test
    void testFindAll() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        
        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

	@Test
    void testFindAllEmpty() {        
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        
        List<Product> result = productService.findAll();

        assertEquals(0, result.size());
    }

	@Test
    void testFindById() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product actual = productService.findById(product.getId());

        assertNotNull(actual);
        assertEquals(product.getId(), actual.getId());
        assertEquals(product.getName(), actual.getName());
    }

	@Test
	void testFindByIdNotFound() {
		long id = anyLong();
		when(productRepository.findById(id)).thenReturn(Optional.empty());

		assertThrowsExactly(ResourceNotFoundException.class, () -> productService.findById(id));
	}
}