package com.myapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.entities.OrderItem;
import com.myapp.entities.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{

}
