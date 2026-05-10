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

        helper.setTo("contact.reservia@gmail.com");
        helper.setReplyTo(email);
        helper.setSubject("New Contact Message from " + name);
        helper.setText(html, true);

        mailSender.send(message);
    }

    public void sendBookingVerifiedEmail(String to, String bookingReference) throws MessagingException {
        Context context = new Context();
        context.setVariable("bookingReference", bookingReference);
        context.setVariable("websiteUrl", "http://localhost:8085/account/bookings");

        String html = templateEngine.process("emails/booking-verified-email", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("Your booking has been verified!");
        helper.setText(html, true);

        mailSender.send(message);
    }
}
