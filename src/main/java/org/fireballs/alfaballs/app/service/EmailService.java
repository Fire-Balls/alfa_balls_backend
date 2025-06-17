package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

    public void sendInviteEmail(String to, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Project Invitation");
        message.setText("You have been invited to a project. Click here to accept: " + link);
        emailSender.send(message);
        log.info("Email sent to {}", to);
    }
}
