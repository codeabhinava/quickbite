package com.example.quickbite.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.quickbite.common.Email;
import com.example.quickbite.model.MenuRegistration;
import com.example.quickbite.model.ResturantMenu;
import com.example.quickbite.model.ResturantModel;
import com.example.quickbite.model.ResturantRegistration;
import com.example.quickbite.model.ResturantRegistrationToken;
import com.example.quickbite.producer.EmailProducer;
import com.example.quickbite.repository.ResturantMenuRepository;
import com.example.quickbite.repository.ResturantModelRepository;
import com.example.quickbite.repository.ResturantRegistrationTokenRepository;

@Service
public class ResturantService {

    private final ResturantModelRepository resturantRepository;
    private final EmailProducer emailProducer;
    private final ResturantRegistrationTokenRepository resturantRegistrationTokenRepository;
    private final ResturantMenuRepository resturantMenuRepository;

    public ResturantService(ResturantModelRepository resturantRepository, EmailProducer emailProducer, ResturantRegistrationTokenRepository resturantRegistrationTokenRepository, ResturantMenuRepository resturantMenuRepository) {
        this.resturantRepository = resturantRepository;
        this.emailProducer = emailProducer;
        this.resturantRegistrationTokenRepository = resturantRegistrationTokenRepository;
        this.resturantMenuRepository = resturantMenuRepository;
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

    public String addMenu(UUID token, List<MenuRegistration> resturantMenu) {
        ResturantModel resturant = resturantRepository.findByConfirmationToken(token);

        for (MenuRegistration menu : resturantMenu) {
            resturantMenuRepository.save(new ResturantMenu(menu.getItemName(), menu.getItemImageUrl(), menu.getItemDescription(), menu.getItemPrice(), menu.getItemCategory(), resturant, true));
        }

        return "MENU ADDED SUCCESSFULLY!";

    }

    public void update(UUID token, String itemName, MenuRegistration menu) {

        ResturantModel resturant = resturantRepository.findByConfirmationToken(token);
        ResturantMenu existingmenu = resturantMenuRepository.findByResturantAndItemName(resturant, itemName).orElseThrow();
        if (menu.getItemCategory() != null) {
            existingmenu.setItemCategory(menu.getItemCategory());
        }
        if (menu.getItemDescription() != null) {
            existingmenu.setItemDescription(menu.getItemDescription());
        }
        if (menu.getItemImageUrl() != null) {
            existingmenu.setItemImageUrl(menu.getItemImageUrl());
        }
        if (menu.getItemName() != null) {
            existingmenu.setItemName(menu.getItemName());
        }
        if (menu.getItemPrice() > 0) {
            existingmenu.setItemPrice(menu.getItemPrice());
        }
        resturantMenuRepository.save(existingmenu);

    }

    public ResturantModel getResturantByToken(UUID token) {
        return resturantRepository.findByConfirmationToken(token);
    }

}
