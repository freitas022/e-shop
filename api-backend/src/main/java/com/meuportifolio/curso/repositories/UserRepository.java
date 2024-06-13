package com.meuportifolio.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meuportifolio.curso.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
