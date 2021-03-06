package com.dinamica.test.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dinamica.test.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param ("email") String email);
	
	@Query("SELECT u FROM User u WHERE u.dni = :dni")
	public User getUserByDni(@Param ("dni") long dni);
}
