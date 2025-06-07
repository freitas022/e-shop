package com.myapp.inventory;

import com.myapp.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity(name = "tb_inventory")
public class Inventory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    @UpdateTimestamp
    private Instant lastUpdate;

    private Integer minQuantity;

    private Integer maxQuantity;

}
