package com.meuportifolio.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meuportifolio.curso.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
