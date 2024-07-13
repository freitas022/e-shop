package com.meuportifolio.eshop.dto;

import com.meuportifolio.eshop.entities.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record ProductDto(
        Long id,
        @NotBlank(message = "Cannot be null or empty")
        String name,
        @NotBlank(message = "Cannot be null or empty")
        String description,
        @NotNull(message = "Cannot be null or empty.")
        @DecimalMin(value = "0.00", inclusive = false, message = "Must be more than zero.")
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
