package com.meuportifolio.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meuportifolio.curso.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
