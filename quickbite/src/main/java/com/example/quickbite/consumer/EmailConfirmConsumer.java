package com.example.quickbite.consumer;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.quickbite.common.Email;
import com.example.quickbite.email.EmailSender;
import com.example.quickbite.model.AppUser;
import com.example.quickbite.model.ResturantModel;
import com.example.quickbite.service.ConfirmationService;
import com.example.quickbite.service.ResturantService;
import com.example.quickbite.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailConfirmConsumer {

    private final EmailSender emailSender;
    private final UserService userService;
    private final ResturantService resturantService;

    public EmailConfirmConsumer(EmailSender emailSender, UserService userService, ResturantService resturantService) {

        this.emailSender = emailSender;
        this.userService = userService;
        this.resturantService = resturantService;
    }

    @KafkaListener(topics = "email-topic", groupId = "email-group")
    public void consumeEmailConfirmation(Email email) {
        log.info("Received email confirmation:{} " + email.confirmationToken());
        UUID uuidToken = email.confirmationToken();
        if (email.type().equalsIgnoreCase("User")) {

            String link = "http://localhost:7070/quickbite/users/confirm?token=" + uuidToken;
            AppUser user = userService.getUserByToken(uuidToken);
            emailSender.send(user.getEmailId(), emailSender.buildEmail(user.getUsername(), link));
        } else {
            String link = "http://localhost:7070/quickbite/resturants/confirm?token=" + uuidToken;
            ResturantModel resturant = resturantService.getResturantByToken(uuidToken);
            emailSender.send(resturant.getResturantEmail(), emailSender.buildEmail(resturant.getResturantName(), link));
        }
    }
}
