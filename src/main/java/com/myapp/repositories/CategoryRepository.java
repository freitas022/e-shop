package com.myapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
