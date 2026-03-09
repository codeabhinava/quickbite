package com.example.quickbite.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.quickbite.model.ConfirmationToken;
import com.example.quickbite.repository.ConfirmationTokenRepository;

@Service
public class ConfirmationService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public String getEmailByToken(UUID token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
        if (confirmationToken != null) {
            return confirmationToken.getAppUser().getEmailId();
        }
        return null;
    }

    public String confirmToken(UUID token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
        if (confirmationToken.getConfirmedAt() != null) {
            return "Already confirmed";
        }
        confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
        return "Token confirmed successfully";
    }
}
