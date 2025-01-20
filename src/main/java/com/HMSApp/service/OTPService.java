package com.HMSApp.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {

    private final Map<String,OTPDetails> otpStorage = new HashMap<>();

    private static final int OTP_EXPIRY_TIME = 5;
    public String generateOTP (String mobileNumber){
        String otp = String .format("%06d",new Random().nextInt(999999));
        OTPDetails otpDetails = new OTPDetails(otp,System.currentTimeMillis());
        otpStorage.put(mobileNumber,otpDetails);
        return otp;
    }

    public static class OTPDetails {
        private final String otp;
        private final long timestamp;

        public OTPDetails(String otp, long timestamp) {
            this.otp = otp;
            this.timestamp = timestamp;
        }
        public String getOtp(){
            return otp;
        }

        public Long getTimestamp(){
            return timestamp;
        }
    }
        public boolean validateOTP(String mobileNumber, String otp) {
            OTPDetails otpDetails = otpStorage.get(mobileNumber);
            if (otpDetails == null) {
                return false;
            }
            long currentTime = System.currentTimeMillis();
            long otpTime = otpDetails.getTimestamp();
            long timeDifference = TimeUnit.MILLISECONDS.toMinutes(currentTime - otpTime);

            if (timeDifference > OTP_EXPIRY_TIME) {
                otpStorage.remove(mobileNumber);
                return false;
            }
            return otpDetails.getOtp().equals(otp);
        }
    }

