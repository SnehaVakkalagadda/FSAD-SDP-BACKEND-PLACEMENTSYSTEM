package com.klef.fsad.sdp.placementsystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsad.sdp.placementsystem.security.JwtUtil;
import com.klef.fsad.sdp.placementsystem.service.AuthService;
import com.klef.fsad.sdp.placementsystem.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService service;
	
	@PostMapping("/send-otp")
	public ResponseEntity<String> sendOtp(@RequestParam String username)
	{
	    String msg = authService.sendOtpAfterLogin(username);

	    return  ResponseEntity.status(200).body(msg);
	}
	
	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(
	        @RequestParam String username,
	        @RequestParam String otp)
	{
	    String msg = authService.verifyOtp(username, otp);

	    if(msg.contains("Successful"))
	    {
	        UserDetails userDetails = service.loadUserByUsername(username);

	        String token = jwtUtil.generateToken(userDetails);

	        Object userObj = service.getUserByLogin(username);

	        return ResponseEntity.ok(
	            Map.of(
	                "token", token,
	                "user", userObj
	            )
	        );
	    }
	    else
	    {
	        return ResponseEntity.status(400).body(msg);
	    }
	}
	@GetMapping("/test")
	public String test()
	{
	    System.out.println("TEST API HIT");
	    return "Working";
	}
}