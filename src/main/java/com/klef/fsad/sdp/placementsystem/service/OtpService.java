package com.klef.fsad.sdp.placementsystem.service;

public interface OtpService {
	public String generateOtp(String email);
    public boolean verifyOtp(String email, String otp);
}
