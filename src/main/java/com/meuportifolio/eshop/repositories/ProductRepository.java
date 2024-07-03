package com.meuportifolio.eshop.repositories;

import com.meuportifolio.eshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT obj FROM Product obj "
            + "WHERE UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%')) ")
    List<Product> findByNameContainingIgnoreCase(String name);
}
