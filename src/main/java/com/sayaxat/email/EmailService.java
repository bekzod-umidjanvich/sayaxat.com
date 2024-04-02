package com.sayaxat.email;//package com.sayaxat.security.email;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final JavaMailSender emailSender;

    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);

    @Override
    @Async
    public void send(String to, String email) {

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setText(email, true);

            // Set the host and port of the SMTP server
            // This is the configuration for MailDev
            ((JavaMailSenderImpl) emailSender).setHost("localhost");
            ((JavaMailSenderImpl) emailSender).setPort(1025);

            emailSender.send(message);

        } catch (Exception e) {
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email", e);
        }
    }
}
