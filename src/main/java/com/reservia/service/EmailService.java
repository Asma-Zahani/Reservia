package com.reservia.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendVerificationEmail(String to, String token) throws MessagingException {
        String link = "http://localhost:8085/auth/verify?token=" + token;

        Context context = new Context();
        context.setVariable("link", link);

        String html = templateEngine.process("emails/verify-email", context);

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Verify your Reservia account");
        helper.setText(html, true);

        mailSender.send(message);
    }

//    public void sendResetPasswordEmail(String to, String token) throws MessagingException {
//        String link = "http://localhost:8085/auth/reset-password?token=" + token;
//    }

    public void sendWelcomeEmail(String to, String name) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("websiteUrl", "http://localhost:8085");

        String html = templateEngine.process("emails/welcome-email", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("Bienvenue sur Reservia");
        helper.setText(html, true);

        mailSender.send(message);
    }

    public void sendContactEmail(String name, String email, String messageContent) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("email", email);
        context.setVariable("messageContent", messageContent);

        String html = templateEngine.process("emails/contact-email", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo("support@reservia.com"); // ou ton email de contact
        helper.setSubject("New Contact Message from " + name);
        helper.setText(html, true);

        mailSender.send(message);
    }
}
