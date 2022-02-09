package com.dinamica.test.implementations;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dinamica.test.dtos.SmsRequest;
import com.dinamica.test.service.twilio.ISmsSenderServices;


@Service
public class SmsServices {
	private final ISmsSenderServices smsSender;

	public SmsServices(@Qualifier("twilio") TwilioSmsSenderServices TwilioSmsSender) {
		this.smsSender = TwilioSmsSender;
	}

	public String sendSms(SmsRequest smsRequest) {
		return smsSender.sendSms(smsRequest);
	}
}
