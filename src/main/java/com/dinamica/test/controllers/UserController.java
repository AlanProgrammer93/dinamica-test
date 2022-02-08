package com.dinamica.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.dinamica.test.models.User;
import com.dinamica.test.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService service;
	
	@PostMapping("/users/check_email")
	public String checkDuplicateEmail(@Param("email") String email) {
		return service.isEmailUnique(email) ? "OK" : "DUPLICATED";
	}

	@PostMapping("/users/save")
	public void saveUser(User user) {
		System.out.println(user);
		service.saveUser(user);
		
	}
}
