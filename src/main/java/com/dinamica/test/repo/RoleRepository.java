package com.dinamica.test.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinamica.test.models.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
