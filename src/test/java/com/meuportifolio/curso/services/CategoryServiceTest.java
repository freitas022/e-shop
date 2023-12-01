package com.meuportifolio.curso.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
// import static org.hamcrest.MatcherAssert.assertThat;

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

import com.meuportifolio.curso.entities.Category;
import com.meuportifolio.curso.repositories.CategoryRepository;
import com.meuportifolio.curso.services.exceptions.ResourceNotFoundException;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category(19191919L, "Smart TV 55");
    }

    @Test
    void testFindAll() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        
        List<Category> result = categoryService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindAllEmpty() {        
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
        
        List<Category> result = categoryService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    void testFindById() {
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        Category actual = categoryService.findById(category.getId());

        assertNotNull(actual);
        assertEquals(category.getId(), actual.getId());
        assertEquals(category.getName(), actual.getName());
    }

    @Test
    void testFindByIdNotFound() {
        long id = anyLong();
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrowsExactly(ResourceNotFoundException.class, () -> categoryService.findById(id));
    }
}