package com.example.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String email,String message) throws Exception{
    	
    	//MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    	
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Response from the Manager");
        mailMessage.setText(message);
     //   mailMessage.
        javaMailSender.send(mailMessage);
    }

}
