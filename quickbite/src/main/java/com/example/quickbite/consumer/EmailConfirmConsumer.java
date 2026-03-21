package com.example.quickbite.consumer;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.quickbite.common.Email;
import com.example.quickbite.email.EmailSender;
import com.example.quickbite.model.AppUser;
import com.example.quickbite.model.RestaurantModel;
import com.example.quickbite.service.ConfirmationService;
import com.example.quickbite.service.RestaurantService;
import com.example.quickbite.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailConfirmConsumer {

    private final EmailSender emailSender;
    private final UserService userService;
    private final RestaurantService restaurantService;

    public EmailConfirmConsumer(EmailSender emailSender, UserService userService, RestaurantService restaurantService) {

        this.emailSender = emailSender;
        this.userService = userService;
        this.restaurantService = restaurantService;
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
            String link = "http://localhost:7070/quickbite/restaurants/confirm?token=" + uuidToken;
            RestaurantModel restaurant = restaurantService.getrestaurantByToken(uuidToken);
            emailSender.send(restaurant.getRestaurantEmail(), emailSender.buildEmail(restaurant.getRestaurantName(), link));
        }
    }
}
