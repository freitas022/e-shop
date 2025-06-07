package com.myapp.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    private final OrderService orderService;

    @Operation(summary = "Get all orders")
    @ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                 schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = OrderDto.class))}
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<OrderDto>> findAll(@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "orderBy", defaultValue = "moment") String orderBy,
                                                  @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        return ResponseEntity.ok().body(orderService.findAll(pageNumber, pageSize, orderBy, direction));
    }


    @Operation(summary = "Get order by id")
    @ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                 schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = OrderDto.class))}
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(orderService.findById(id));
    }

    @Operation(summary = "Create a new order")
    @ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = OrderDto.class))}
    )
    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto orderDto) throws JsonProcessingException {
        return ResponseEntity.ok().body(orderService.create(orderDto));
    }

    @Operation(summary = "Create a new order from user cart")
    @ApiResponse(content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = OrderDto.class))}
    )
    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> createFromCart(@AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createFromCart(userEmail));
    }
}
