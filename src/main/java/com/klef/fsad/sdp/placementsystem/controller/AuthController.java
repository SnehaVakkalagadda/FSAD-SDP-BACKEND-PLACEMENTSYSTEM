package com.klef.fsad.sdp.placementsystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.fsad.sdp.placementsystem.dto.AuthRequestDTO;
import com.klef.fsad.sdp.placementsystem.security.JwtUtil;
import com.klef.fsad.sdp.placementsystem.service.AuthService;
import com.klef.fsad.sdp.placementsystem.service.UserService;

@RestController
@RequestMapping("/auth")

public class AuthController 
{
    @Autowired
    private UserService service;
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) 
    {
        try 
        {
            UserDetails userDetails = service.loadUserByUsername(request.getLogin());

            String role = userDetails.getAuthorities()
                    .iterator().next().getAuthority();

            if (!role.equalsIgnoreCase(request.getRole()))
            {
                return ResponseEntity.status(403).body("Invalid Role");
            }

            boolean isValid = false;

            if (role.equalsIgnoreCase("ADMIN"))
            {
                isValid = request.getPassword().equals(userDetails.getPassword());
            }
            else if (role.equalsIgnoreCase("STUDENT") || 
                     role.equalsIgnoreCase("EMPLOYER") || 
                     role.equalsIgnoreCase("PLACEMENT OFFICER"))
            {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                isValid = encoder.matches(request.getPassword(), userDetails.getPassword());
            }

            if (!isValid)
            {
                return ResponseEntity.status(401).body("Login Invalid");
            }

            // SEND OTP HERE
            authService.sendOtpAfterLogin(request.getLogin());

            return ResponseEntity.ok("OTP Sent to Email");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    
}