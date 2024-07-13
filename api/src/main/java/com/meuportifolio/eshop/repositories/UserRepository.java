package com.meuportifolio.eshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meuportifolio.eshop.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
