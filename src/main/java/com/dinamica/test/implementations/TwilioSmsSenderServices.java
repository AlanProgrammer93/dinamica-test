package com.dinamica.test.implementations;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dinamica.test.dtos.SmsRequest;
import com.dinamica.test.service.twilio.ISmsSenderServices;
import com.dinamica.test.twilio.TwilioConfiguration;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;


@Service("twilio")
public class TwilioSmsSenderServices implements ISmsSenderServices {
	private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSenderServices.class);
    private final TwilioConfiguration twilioConfiguration;
    public TwilioSmsSenderServices(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }
    @Override
    public String sendSms(SmsRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getTo())) {
            PhoneNumber to = new PhoneNumber(smsRequest.getTo());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            
            int otp = (int)(Math.random()*9000)+1000;
            String message = String.valueOf(otp);
            
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            LOGGER.info("Send sms {}", smsRequest);
            
            return message;
        } else {
            throw new IllegalArgumentException(
                    "Phone number " + smsRequest.getTo() + " is not a valid number"
            );
        }
    }
    private boolean isPhoneNumberValid(String phoneNumber) {
        final String pattern = "^\\+(?:[0-9]●?){6,14}[0-9]$";
        //final Pattern pattern = Pattern.compile("^\\+(?:[0-9]●?){6,14}[0-9]$");
        return Pattern.matches(pattern, phoneNumber);
    }
}
