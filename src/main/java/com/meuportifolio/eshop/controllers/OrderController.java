package com.meuportifolio.eshop.controllers;

import com.meuportifolio.eshop.dto.OrderDto;
import com.meuportifolio.eshop.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
@Tag(name = "Order", description = "Endpoints for managing orders.")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Should return the list of orders")
    @ApiResponse(responseCode = "200", description = "Should return the list of orders found if present or empty list.")
    @GetMapping
    public List<OrderDto> findAll() {
        return orderService.findAll();
    }

    @Operation(summary = "Should return only one order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "204", description = "Should return order not found exception."),
            @ApiResponse(responseCode = "400", description = "Should return bad request.")
    })
    @GetMapping(value = "/{id}")
    public OrderDto findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto insert(@Valid @RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }
}
