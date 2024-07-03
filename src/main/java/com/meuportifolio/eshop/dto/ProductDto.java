package com.meuportifolio.eshop.dto;

import com.meuportifolio.eshop.entities.Product;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record ProductDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String imgUrl,
        Set<CategoryDto> categories
) {

    public ProductDto(Product entity) {
        this(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice(), entity.getImgUrl(),
                entity.getCategories()
                        .stream()
                        .map(CategoryDto::new)
                        .collect(Collectors.toSet()));
    }

}
