package com.congreso.domain.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailService {

    private static final Logger log = LogManager.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Synchronous method (plain text)
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = createMessage(to, subject, body);
            log.info("Sending email synchronously...");
            mailSender.send(message);
            log.info("Email sent synchronously");
        } catch (Exception ex) {
            log.error("Error sending email synchronously: {}", ex.getMessage(), ex);
        }
    }

    // Asynchronous method (plain text)
    @Async
    public CompletableFuture<Void> sendEmailAsync(String to, String subject, String body) {
        try {
            SimpleMailMessage message = createMessage(to, subject, body);
            log.info("Sending email asynchronously...");
            mailSender.send(message);
            log.info("Email sent asynchronously");
            return CompletableFuture.completedFuture(null);
        } catch (Exception ex) {
            log.error("Error sending email asynchronously: {}", ex.getMessage(), ex);
            return CompletableFuture.failedFuture(ex);
        }
    }

    private SimpleMailMessage createMessage(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("webbank404@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        return message;
    }

    // --- New: HTML support using MimeMessageHelper ---

    // Synchronous HTML send
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom("webbank404@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = isHtml
            log.info("Sending HTML email synchronously...");
            mailSender.send(mimeMessage);
            log.info("HTML email sent synchronously");
        } catch (Exception ex) {
            log.error("Error sending HTML email synchronously: {}", ex.getMessage(), ex);
        }
    }

    // Asynchronous HTML send
    @Async
    public CompletableFuture<Void> sendHtmlEmailAsync(String to, String subject, String htmlBody) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom("webbank404@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = isHtml
            log.info("Sending HTML email asynchronously...");
            mailSender.send(mimeMessage);
            log.info("HTML email sent asynchronously");
            return CompletableFuture.completedFuture(null);
        } catch (Exception ex) {
            log.error("Error sending HTML email asynchronously: {}", ex.getMessage(), ex);
            return CompletableFuture.failedFuture(ex);
        }
    }

}
