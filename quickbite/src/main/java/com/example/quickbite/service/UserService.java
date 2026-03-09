package com.example.quickbite.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.quickbite.model.AppUser;
import com.example.quickbite.model.ConfirmationToken;
import com.example.quickbite.model.UserRegistration;
import com.example.quickbite.producer.EmailProducer;
import com.example.quickbite.repository.AppUserRepository;
import com.example.quickbite.repository.ConfirmationTokenRepository;

@Service
public class UserService {

    private final AppUserRepository appUserRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailProducer emailProducer;

    public UserService(AppUserRepository appUserRepository, ConfirmationTokenRepository confirmationTokenRepository, EmailProducer emailProducer) {
        this.appUserRepository = appUserRepository;

        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailProducer = emailProducer;
    }

    public AppUser getUserByToken(UUID token) {
        return appUserRepository.findByToken(token);
    }

    public String createUser(UserRegistration userRegistration) {
        UUID confirmationToken = UUID.randomUUID();
        AppUser newUser = new AppUser(userRegistration.getName(), userRegistration.getEmailId(), userRegistration.getPassword(), userRegistration.getPhoneNumber(), confirmationToken);
        appUserRepository.save(newUser);
        confirmationTokenRepository.save(new ConfirmationToken(confirmationToken, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), newUser));
        emailProducer.sendEmailConfirmation(userRegistration.getEmailId(), confirmationToken);
        return "User created successfully";
    }

}
