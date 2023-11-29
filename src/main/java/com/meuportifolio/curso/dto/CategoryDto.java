package com.meuportifolio.curso.dto;

import java.util.Set;

import com.meuportifolio.curso.entities.Category;
import com.meuportifolio.curso.entities.Product;

public record CategoryDto(Long id, String name, Set<Product> products) {
    
    public CategoryDto(Category entity) {
        this(entity.getId(), entity.getName(), entity.getProducts());
    }
}
