package com.myapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString(exclude = {"items"})
@Entity(name = "tb_cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "id.cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new LinkedList<>();

    /**
     * method to add a product to the cart
     */
    public void addItem(Product product, int quantity) {
        Optional<CartItem> existingItem = items.stream()
                .filter(item -> item.getId().getProduct().equals(product))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            items.add(new CartItem(this, product, quantity));
        }
    }

    /**
     * method to remove a product from the cart
     */
    public void removeItem(Product product) {
        items.removeIf(item -> item.getId().getProduct().equals(product));
    }

    public Double getTotal() {
        return items.stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();
    }

}
