package com.myapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
