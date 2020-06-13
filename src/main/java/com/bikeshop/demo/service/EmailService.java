package com.bikeshop.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private String MESSAGE_FROM = "BikeShop";

    private JavaMailSender javaMailSender;


    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMessage(String email, String username) {
        SimpleMailMessage simpleMailMessage = null;
        try {
            simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(MESSAGE_FROM);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("Registration success to BikeShop!");
            simpleMailMessage.setText("Dear" + username + ",/n" + "Registration complete.");
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            logger.error("Error sending message to address: " + email + "  " + e);
        }
    }

}

