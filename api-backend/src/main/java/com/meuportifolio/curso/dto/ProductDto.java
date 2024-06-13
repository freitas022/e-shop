package com.meuportifolio.curso.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.meuportifolio.curso.entities.Category;
import com.meuportifolio.curso.entities.Product;

public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imgUrl;
    private Set<CategoryDto> categories = new HashSet<>();

    public ProductDto(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        this.categories = convertToDto(entity.getCategories());
    }

    private Set<CategoryDto> convertToDto(Set<Category> categoryList) {
        return categoryList.stream()
                .map(CategoryDto::new)
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Set<CategoryDto> getCategories() {
        return categories;
    }

}
