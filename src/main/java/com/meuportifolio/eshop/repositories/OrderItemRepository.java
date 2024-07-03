package com.meuportifolio.eshop.repositories;

import com.meuportifolio.eshop.entities.OrderItem;
import com.meuportifolio.eshop.entities.pk.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
