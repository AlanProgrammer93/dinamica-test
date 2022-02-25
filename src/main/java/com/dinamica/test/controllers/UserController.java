package com.dinamica.test.controllers;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dinamica.test.Utility;
import com.dinamica.test.models.User;
import com.dinamica.test.service.UserService;

@Controller
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserService service;
	
	@PostMapping("/users/check_email")
	public ResponseEntity<?> checkDuplicateEmail(@Param("email") String email) throws UnsupportedEncodingException, MessagingException {
		User user = service.isEmailUnique(email);
		
		if(user == null) {
			sendVerificationEmail();
			return ResponseEntity.ok("OK!");
		} else {
			//return ResponseEntity.status(HttpStatus.CREATED);
			return ResponseEntity
                    .badRequest()
                    .body("YA EXISTE EL EMAIL");
		}
	}
	
	@PostMapping("/users/check_dni")
	public ResponseEntity<?> checkDuplicateDni(@Param("dni") String dni) {
		User user = service.isDniUnique(dni);
		
		if(user == null) {
			return ResponseEntity.ok("OK!");
		} else {
			return ResponseEntity
                    .badRequest()
                    .body("YA EXISTE EL DNI");
		}
	}

	@PostMapping("/users/save")
	public ResponseEntity<?> saveUser(User user) {
		System.out.println(user);
		service.saveUser(user);
		return ResponseEntity.ok("OK!");
	}
	
	private void sendVerificationEmail() throws UnsupportedEncodingException, MessagingException {
		
		JavaMailSenderImpl mailSender = Utility.prepareMailSender();
		
		String toAddress = "capitanreact@gmail.com";
		String subject = "Probando";
		String content = "Probando Content";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("alanzurita.dinamica@gmail.com", "Dinamica TEAM");
		helper.setTo(toAddress);
		helper.setSubject(subject);
		helper.setText(content, true);
		
		mailSender.send(message);
		
	}
	
}
