package com.meuportifolio.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meuportifolio.curso.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
