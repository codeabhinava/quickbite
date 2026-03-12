package com.example.quickbite.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.quickbite.model.ResturantModel;
import com.example.quickbite.model.ResturantRegistrationToken;
import com.example.quickbite.producer.EmailProducer;
import com.example.quickbite.repository.ResturantRegistrationTokenRepository;

import jakarta.transaction.Transactional;

import com.example.quickbite.repository.ResturantModelRepository;

@Service
public class ResturantRegistrationService {

    private final ResturantModelRepository resturantRepository;
    private final ResturantRegistrationTokenRepository resturantRegistrationTokenRepository;

    public ResturantRegistrationService(ResturantModelRepository resturantRepository, ResturantRegistrationTokenRepository resturantRegistrationTokenRepository) {
        this.resturantRepository = resturantRepository;
        this.resturantRegistrationTokenRepository = resturantRegistrationTokenRepository;

    }

    @Transactional
    public String confirmToken(UUID token) {
        ResturantRegistrationToken resturantRegistrationToken = resturantRegistrationTokenRepository.findByToken(token).orElseThrow(() -> new IllegalStateException("Token not found"));
        if (resturantRegistrationToken == null) {
            return "Invalid token!";
        }
        resturantRegistrationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
        resturantRepository.updateIsConfirmedBy(token);
        return "Resturant confirmed successfully!";
    }
}
