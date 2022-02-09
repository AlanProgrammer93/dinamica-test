package com.dinamica.test.service.twilio;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.dinamica.test.dtos.SmsRequest;

public interface ITwilioSmsController {
	public Map<String, String> sendSms(@RequestBody SmsRequest smsRequest);
	// verificar otp
	public Boolean sendVerified(@RequestBody SmsRequest smsRequest);
}
