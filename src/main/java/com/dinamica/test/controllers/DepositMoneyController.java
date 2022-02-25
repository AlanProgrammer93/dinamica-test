package com.dinamica.test.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api")
public class DepositMoneyController {
	
	@GetMapping("/ingreso_dinero")
	public ResponseEntity<?> saveDeposit(Authentication authentication) {
		System.out.println("LLEGA HASTA AQUI");
		System.out.println(authentication.getName());
		
		return ResponseEntity.ok("OK!");
	}
}
