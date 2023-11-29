package com.meuportifolio.curso.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.meuportifolio.curso.entities.Category;
import com.meuportifolio.curso.entities.Product;

public record ProductDto(Long id,
        String name,
        String description,
        BigDecimal price,
        String imgUrl,
        Set<Category> categories) {

    public ProductDto(Product entity) {
        this(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice(),
                entity.getImgUrl(), entity.getCategories());
    }

}
