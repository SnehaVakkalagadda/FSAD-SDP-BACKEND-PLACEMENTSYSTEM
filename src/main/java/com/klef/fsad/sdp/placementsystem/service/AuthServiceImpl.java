package com.klef.fsad.sdp.placementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.placementsystem.entity.Admin;
import com.klef.fsad.sdp.placementsystem.entity.Employer;
import com.klef.fsad.sdp.placementsystem.entity.PlacementOfficer;
import com.klef.fsad.sdp.placementsystem.entity.Student;

@Service
public class AuthServiceImpl implements AuthService
{
    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    //  After login success
    @Override
    public String sendOtpAfterLogin(String username) 
    {
        Object user = userService.getUserByLogin(username);

        if(user == null)
        {
            return "User Not Found";
        }

        String email = "";

        if(user instanceof Student)
            email = ((Student) user).getEmail();

        else if(user instanceof Employer)
            email = ((Employer) user).getEmail();

        else if(user instanceof PlacementOfficer)
            email = ((PlacementOfficer) user).getEmail();

        else if(user instanceof Admin)
            email = ((Admin) user).getUsername(); // if admin uses username

        otpService.generateOtp(email);

        return "OTP Sent Successfully";
    }

    // Verify OTP
    @Override
    public String verifyOtp(String username, String otp) 
    {
        Object user = userService.getUserByLogin(username);

        if(user == null)
            return "User Not Found";

        String email = "";

        if(user instanceof Student)
            email = ((Student) user).getEmail();

        else if(user instanceof Employer)
            email = ((Employer) user).getEmail();

        else if(user instanceof PlacementOfficer)
            email = ((PlacementOfficer) user).getEmail();

        else if(user instanceof Admin)
            email = ((Admin) user).getUsername();

        boolean valid = otpService.verifyOtp(email, otp);

        if(valid)
            return "Login Successful";
        else
            return "Invalid OTP";
    }
}
