package com.example.quickbite.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.quickbite.common.Email;
import com.example.quickbite.model.ResturantRegistration;
import com.example.quickbite.model.ResturantModel;
import com.example.quickbite.model.ResturantRegistrationToken;
import com.example.quickbite.producer.EmailProducer;
import com.example.quickbite.repository.ResturantModelRepository;
import com.example.quickbite.repository.ResturantRegistrationTokenRepository;

@Service
public class ResturantService {

    private ResturantModelRepository resturantRepository;
    private EmailProducer emailProducer;
    private ResturantRegistrationTokenRepository resturantRegistrationTokenRepository;

    public ResturantService(ResturantModelRepository resturantRepository, EmailProducer emailProducer, ResturantRegistrationTokenRepository resturantRegistrationTokenRepository) {
        this.resturantRepository = resturantRepository;
        this.emailProducer = emailProducer;
        this.resturantRegistrationTokenRepository = resturantRegistrationTokenRepository;
    }

    public String addResturant(ResturantRegistration resturantRegistration) {

        if (resturantRepository.findByResturantEmail(resturantRegistration.getResturantEmail()) != null) {
            return "Resturant with this email already exists!";
        }
        UUID confirmationToken = UUID.randomUUID();
        ResturantModel resturantModel = new ResturantModel();
        resturantModel.setResturantName(resturantRegistration.getResturantName());
        resturantModel.setResturantAddress(resturantRegistration.getResturantAddress());
        resturantModel.setResturantPhoneNumber(resturantRegistration.getResturantPhoneNumber());
        resturantModel.setResturantEmail(resturantRegistration.getResturantEmail());
        resturantModel.setResturantCuisineType(resturantRegistration.getResturantCuisineType());
        resturantModel.setConfirmationToken(confirmationToken);

        resturantRepository.save(resturantModel);
        resturantRegistrationTokenRepository.save(new ResturantRegistrationToken(confirmationToken, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), resturantModel));
        Email email = new Email("Resturant", resturantRegistration.getResturantEmail(), confirmationToken);
        emailProducer.sendEmailConfirmation(email);
        return "Application received! We will review your application and get back to you soon.";

    }

    public ResturantModel getResturantByToken(UUID token) {
        return resturantRepository.findByConfirmationToken(token);
    }

}
