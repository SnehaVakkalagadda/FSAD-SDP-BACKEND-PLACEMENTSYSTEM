package com.klef.fsad.sdp.placementsystem.service;

public interface AuthService {
    public String sendOtpAfterLogin(String username);
    public String verifyOtp(String username, String otp);
}