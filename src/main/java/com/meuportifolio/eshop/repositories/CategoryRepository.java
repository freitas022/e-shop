package com.meuportifolio.eshop.repositories;

import com.meuportifolio.eshop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
