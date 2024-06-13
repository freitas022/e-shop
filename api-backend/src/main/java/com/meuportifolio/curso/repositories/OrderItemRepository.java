package com.meuportifolio.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meuportifolio.curso.entities.OrderItem;
import com.meuportifolio.curso.entities.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{

}
