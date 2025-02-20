package com.myapp.services;

import com.myapp.dtos.AddToCartRequestDto;
import com.myapp.dtos.RemoveFromCartRequestDto;
import com.myapp.entities.Cart;
import com.myapp.entities.Product;
import com.myapp.entities.User;
import com.myapp.repositories.CartRepository;
import com.myapp.repositories.ProductRepository;
import com.myapp.repositories.UserRepository;
import com.myapp.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public Cart getCartByUser(String authEmail) {
        User user = userRepository.findByEmail(authEmail)
                .filter(u -> u.getEmail().equals(authEmail))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    var newCart = new Cart();
                    newCart.setUser(user);
                    cartRepository.save(newCart);
                    return newCart;
                });
    }


    public Cart addToCart(String email, AddToCartRequestDto request) {
        Cart cart = getCartByUser(email);
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException(request.productId()));

        cart.addItem(product, request.quantity());
        return cartRepository.save(cart);
    }

    public Cart removeFromCart(String email, RemoveFromCartRequestDto request) {
        Cart cart = getCartByUser(email);
        cart.removeItem(productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException(request.productId())));
        return cartRepository.save(cart);
    }

    public void clearCart(String userEmail) {
        Cart cart = getCartByUser(userEmail);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

}
