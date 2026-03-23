package com.example.quickbite.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.quickbite.model.RestaurantRegistrationToken;
import com.example.quickbite.repository.RestaurantModelRepository;
import com.example.quickbite.repository.RestaurantRegistrationTokenRepository;

import jakarta.transaction.Transactional;

@Service
public class RestaurantRegistrationService {

    private final RestaurantModelRepository restaurantRepository;
    private final RestaurantRegistrationTokenRepository restaurantRegistrationTokenRepository;

    public RestaurantRegistrationService(RestaurantModelRepository restaurantRepository, RestaurantRegistrationTokenRepository restaurantRegistrationTokenRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantRegistrationTokenRepository = restaurantRegistrationTokenRepository;

    }

    @Transactional
    public String confirmToken(UUID token) {
        RestaurantRegistrationToken restaurantRegistrationToken = restaurantRegistrationTokenRepository.findByToken(token).orElseThrow(() -> new IllegalStateException("Token not found"));
        if (restaurantRegistrationToken.getConfirmedAt() != null) {
            return "Restaurant already Confirmed";
        }
        if (LocalDateTime.now().isAfter(restaurantRegistrationToken.getExpiresAt())) {
            return "token expired";
        }
        restaurantRegistrationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
        restaurantRepository.updateIsConfirmedBy(token);
        return "restaurant confirmed successfully!";
    }
}
