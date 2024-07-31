package com.meuportifolio.eshop.repositories;

import com.meuportifolio.eshop.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByAuthority(String authority);
}