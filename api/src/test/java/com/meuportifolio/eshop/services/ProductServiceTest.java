package com.meuportifolio.eshop.services;

import com.meuportifolio.eshop.dto.ProductDto;
import com.meuportifolio.eshop.entities.Product;
import com.meuportifolio.eshop.repositories.ProductRepository;
import com.meuportifolio.eshop.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(19191919L, "Smart TV 55", "Lorem ipsum dollor sit amet", BigDecimal.valueOf(3200.0), "");
    }

    @Test
    void testFindAll() {

        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductDto> result = productService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindAllEmpty() {

        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<ProductDto> result = productService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    void testFindById() {

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        ProductDto actual = productService.findById(product.getId());

        assertNotNull(actual);
        assertEquals(product.getId(), actual.id());
        assertEquals(product.getName(), actual.name());
    }

    @Test
    void testFindByIdNotFound() {

        long nonExistingId = 12L;

        when(productRepository.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> productService.findById(nonExistingId));
    }

    @Test
    void testFindByNameContainingIgnoreCase() {

        when(productRepository.findByNameContainingIgnoreCase("Smart")).thenReturn(List.of(product));

        List<ProductDto> dtoList = productService.findByName("Smart");

        assertEquals(1, dtoList.size());
        assertEquals("Smart TV 55", dtoList.getFirst().name());
    }
}