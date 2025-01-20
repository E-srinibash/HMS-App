package com.HMSApp.service;

import com.HMSApp.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    @Autowired
    private TwilioConfig twilioConfig;



    public void sendSms(String phoneNumber, String messageBody) {
        try {
            // Validate phone number  
            if (!isValidPhoneNumber(phoneNumber)) {
                logger.error("Invalid phone number: {}", phoneNumber);
                return;
            }

            // Send SMS  
            Message message = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(twilioConfig.getTwilioPhoneNumber()),
                    messageBody
            ).create();

            logger.info("SMS sent successfully. SID: {}", message.getSid());

        } catch (Exception e) {
            logger.error("Error sending SMS to {}: {}", phoneNumber, e.getMessage());
        }
    }



    private boolean isValidPhoneNumber(String phoneNumber) {
        // Basic phone number validation
        return phoneNumber != null &&
                phoneNumber.matches("\\+?[1-9]\\d{1,14}$");
    }

  }