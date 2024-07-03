package com.meuportifolio.eshop.repositories;

import com.meuportifolio.eshop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
