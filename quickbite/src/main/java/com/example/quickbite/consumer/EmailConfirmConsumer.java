package com.example.quickbite.consumer;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.quickbite.email.EmailSender;
import com.example.quickbite.model.AppUser;
import com.example.quickbite.service.ConfirmationService;
import com.example.quickbite.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailConfirmConsumer {

    private final EmailSender emailSender;
    private final UserService userService;

    public EmailConfirmConsumer(EmailSender emailSender, UserService userService) {

        this.emailSender = emailSender;
        this.userService = userService;
    }

    @KafkaListener(topics = "email-topic", groupId = "email-group")
    public void consumeEmailConfirmation(String token) {
        log.info("Received email confirmation:{} " + token);
        UUID uuidToken = UUID.fromString(token);
        String link = "http://localhost:7070/users/confirm?token=" + uuidToken;
        AppUser user = userService.getUserByToken(uuidToken);
        emailSender.send(user.getEmailId(), emailSender.buildEmail(user.getUserName(), link));
    }
}
