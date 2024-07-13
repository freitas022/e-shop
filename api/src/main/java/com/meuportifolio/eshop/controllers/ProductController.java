package com.meuportifolio.eshop.controllers;

import com.meuportifolio.eshop.dto.ProductDto;
import com.meuportifolio.eshop.dto.ProductListDto;
import com.meuportifolio.eshop.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
@Tag(name = "Product", description = "Endpoints for managing products.")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Should return the list of products")
    @ApiResponse(responseCode = "200", description = "Should return the list of products found if present or empty list.")
    @GetMapping
    public ProductListDto findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "pageSize", defaultValue = "12") int pageSize) {
        return productService.findAll(page, pageSize);
    }

    @Operation(summary = "Should return only one product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "204", description = "Should return product not found exception."),
            @ApiResponse(responseCode = "400", description = "Should return bad request.")
    })
    @GetMapping(value = "/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @Operation(summary = "Should return a list of products if contains determined word in request param")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
    })
    @GetMapping(value = "/search")
    public List<ProductDto> findByName(@RequestParam(name = "name", defaultValue = "") String name) {
        return productService.findByName(name);
    }
}
