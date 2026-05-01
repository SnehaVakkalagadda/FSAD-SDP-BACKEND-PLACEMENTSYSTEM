package com.klef.fsad.sdp.placementsystem.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpServiceImpl implements OtpService
{
    private Map<String, String> otpStorage = new HashMap<>();

    @Autowired
    private EmailService emailService;

    //  Generate OTP
    @Override
    public String generateOtp(String email) 
    {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6 digit OTP

        String otpStr = String.valueOf(otp);

        otpStorage.put(email, otpStr);

        //  Send Email
        emailService.sendEmail(
                email,
                "OTP Verification",
                "Your OTP is: " + otpStr
        );

        return "OTP Sent Successfully";
    }

    // Verify OTP
    @Override
    public boolean verifyOtp(String email, String otp) 
    {
        if(otpStorage.containsKey(email))
        {
            String storedOtp = otpStorage.get(email);

            if(storedOtp.equals(otp))
            {
                otpStorage.remove(email); // remove after success
                return true;
            }
        }

        return false;
    }

}

