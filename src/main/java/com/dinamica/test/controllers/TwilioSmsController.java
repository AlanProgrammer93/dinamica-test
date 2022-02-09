package com.dinamica.test.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dinamica.test.dtos.SmsRequest;
import com.dinamica.test.implementations.SmsServices;
import com.dinamica.test.service.twilio.ITwilioSmsController;
import com.twilio.type.PhoneNumber;

@RestController
@RequestMapping("/api/users/sms")
public class TwilioSmsController implements ITwilioSmsController {
	private final SmsServices smsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public TwilioSmsController(SmsServices smsService) {
		this.smsService = smsService;
	}

	@Override
	@PostMapping(path = "/send")
	public Map<String, String> sendSms(@RequestBody SmsRequest smsRequest) {
		String otp = this.smsService.sendSms(smsRequest);

		PhoneNumber to = new PhoneNumber(smsRequest.getTo());
		/*
		 * Calendar calendar = Calendar.getInstance(); int ttl = 60 * 2;
		 * calendar.setTime(calendar.getTime()); calendar.add(Calendar.MINUTE, ttl);
		 * Date expires = calendar.getTime();
		 */

		String data = otp + "." + String.valueOf(to);
		String encodedData = passwordEncoder.encode(data);
		
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		String access_token = JWT.create()
				.withSubject(data)
				.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
				.sign(algorithm);

		HashMap<String, String> map = new HashMap<>();
		map.put("hash", encodedData);
		map.put("otp", otp);
		map.put("access_token", access_token);
		return map;
	}

	@Override
	@PostMapping(path = "/check")
	public Boolean sendVerified(SmsRequest smsRequest) {

		PhoneNumber to = new PhoneNumber(smsRequest.getTo());
		String otp = smsRequest.getMessage();
		String token = smsRequest.getToken();

		
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(token); 
		String username = decodedJWT.getSubject();
		
		System.out.println(username);

		return true;
	}

}
