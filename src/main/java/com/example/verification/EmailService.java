package com.example.verification;

import com.example.configurations.ApplicationConfiguration;
import com.example.dtos.UserDTO;
import com.example.services.UserService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class EmailService {
    private JavaMailSender mailSender;

    private ApplicationConfiguration configuration;

    private UserService userService;

    public EmailService(JavaMailSender mailSender, ApplicationConfiguration configuration, UserService userService) {
        this.mailSender = mailSender;
        this.configuration = configuration;
        this.userService = userService;
    }

    public void sendEmail(UserDTO userDTO) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.setFrom("admin@redbay.com");
            message.setRecipients(MimeMessage.RecipientType.TO, userDTO.getEmail());
            message.setSubject("Email verification required");

            String token = getToken();
            userService.saveVerificationTokenForUser(userDTO, token);

            String button = "<a href=\"" + configuration.getDomain() + "/verify-email?token=" + token + "\" style=\"background:#3630a3;color:white;\"" + ">" + "Verify email</a>";

            String htmlContent = "<h1>Dear " + userDTO.getName() + ",</h1>"
                    + "<p>We have received a registration attempt using your email address. If the attempt was made by you, please click the link below to verify your email address and begin using our app.</p>"
                    + button;
            message.setContent(htmlContent, "text/html; charset=utf-8");
        } catch (MessagingException exception) {
            throw exception;
        }

        mailSender.send(message);
    }

    private String getToken() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 30;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
