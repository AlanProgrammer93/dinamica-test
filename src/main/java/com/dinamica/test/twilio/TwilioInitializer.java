package com.dinamica.test.twilio;

import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

@Configuration
public class TwilioInitializer {
	//private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);
	private final TwilioConfiguration twilioConfiguration;

	public TwilioInitializer(TwilioConfiguration twilioConfiguration) {
		this.twilioConfiguration = twilioConfiguration;
		Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
	}
}
