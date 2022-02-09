package com.dinamica.test.service.twilio;

import com.dinamica.test.dtos.SmsRequest;

public interface ISmsSenderServices {
	String sendSms(SmsRequest smsRequest);
}
