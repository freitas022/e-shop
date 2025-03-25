package com.myapp.cart;

import com.myapp.auth.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Endpoints to manage the shopping cart")
public class CartController {

    private final CartService cartService;
    private final SecurityUtils securityUtils;

    @Operation(summary = "Get user's cart")
    @ApiResponse(content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = CartDto.class))})
    @GetMapping("/my-cart")
    public ResponseEntity<CartDto> getCart() {
        var user = securityUtils.getAuthenticatedUsername();
        var result = cartService.getCartByUser(user.email());
        return ResponseEntity.ok().body(new CartDto(result));
    }

    @Operation(summary = "Add product to cart")
    @ApiResponse(content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = AddToCartRequestDto.class))})
    @PostMapping("/add")
    public ResponseEntity<CartDto> addToCart(@RequestBody AddToCartRequestDto request) {
        var user = securityUtils.getAuthenticatedUsername();
        var result = cartService.addToCart(user.email(), request);
        return ResponseEntity.ok(new CartDto(result));
    }

    @Operation(summary = "Remove product from Cart")
    @ApiResponse(content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = RemoveFromCartRequestDto.class))})
    @DeleteMapping("/remove")
    public ResponseEntity<CartDto> removeFromCart(@RequestBody RemoveFromCartRequestDto request) {
        var user = securityUtils.getAuthenticatedUsername();
        var result = cartService.removeFromCart(user.email(), request);
        return ResponseEntity.ok(new CartDto(result));
    }

    @Operation(summary = "Clear the cart")
    @ApiResponse(responseCode = "204")
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        var user = securityUtils.getAuthenticatedUsername();
        cartService.clearCart(user.email());
        return ResponseEntity.noContent().build();
    }
}

