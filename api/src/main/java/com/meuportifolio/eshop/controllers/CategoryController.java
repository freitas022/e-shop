package com.meuportifolio.eshop.controllers;

import com.meuportifolio.eshop.dto.CategoryDto;
import com.meuportifolio.eshop.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
@Tag(name = "Category", description = "Endpoints for managing categories.")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Should return the list of categories")
    @ApiResponse(responseCode = "200", description = "Should return the list of categories found if present or empty list.")
    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @Operation(summary = "Should return only one category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "404", description = "Should return category not found exception."),
            @ApiResponse(responseCode = "400", description = "Should return bad request.")
    })
    @GetMapping(value = "/{id}")
    public CategoryDto findById(@PathVariable @Positive Long id) {
        return categoryService.findById(id);
    }
}
