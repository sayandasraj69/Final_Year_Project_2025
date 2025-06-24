package com.sayandas.Final.Year.Project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class NotificationService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    // Dummy SMS sender (replace with real API integration)
    public void sendSms(String phoneNumber, String message) {
        // Integrate with SMS provider like Twilio here
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
}
