package com.klef.fsad.sdp.placementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.placementsystem.service.OtpService;

@RestController
@RequestMapping("/otp")
@CrossOrigin(origins = "*")
public class OtpController 
{
    @Autowired
    private OtpService otpService;

    // Send OTP
    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestParam String email)
    {
        String msg = otpService.generateOtp(email);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // Verify OTP
    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp)
    {
        boolean valid = otpService.verifyOtp(email, otp);

        if(valid)
            return new ResponseEntity<>("OTP Verified Successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Invalid OTP", HttpStatus.BAD_REQUEST);
    }
}