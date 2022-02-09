package com.dinamica.test.dtos;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SmsRequest {
	@NotBlank
	private final String to;
	@NotBlank
	private final String message;
	
	private final String token;

	public SmsRequest(@JsonProperty("to") String to, @JsonProperty("message") String message, @JsonProperty("token") String token) {
		this.to = to;
		this.message = message;
		this.token = token;
	}

	@Override
	public String toString() {
		return "SmsRequest{to: " + to + ", message: " + message + "}";
	}

	public String getTo() {
		return to;
	}

	public String getMessage() {
		return message;
	}
	
	public String getToken() {
		return token;
	}
}
