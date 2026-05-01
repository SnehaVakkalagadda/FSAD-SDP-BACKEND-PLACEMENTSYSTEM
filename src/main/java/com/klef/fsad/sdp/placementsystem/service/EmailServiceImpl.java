package com.klef.fsad.sdp.placementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService
{
	@Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String text) 
    {
        try
        {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
        }
        catch(Exception e)
        {
            System.out.println("Email sending failed");
        }
    }
}