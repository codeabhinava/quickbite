package com.example.quickbite.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.quickbite.common.Email;
import com.example.quickbite.model.MenuRegistration;
import com.example.quickbite.model.RestaurantMenu;
import com.example.quickbite.model.RestaurantModel;
import com.example.quickbite.model.RestaurantRegistration;
import com.example.quickbite.model.RestaurantRegistrationToken;
import com.example.quickbite.producer.EmailProducer;
import com.example.quickbite.repository.RestaurantMenuRepository;
import com.example.quickbite.repository.RestaurantModelRepository;
import com.example.quickbite.repository.RestaurantRegistrationTokenRepository;

@Service
public class RestaurantService {

    private final RestaurantModelRepository restaurantRepository;
    private final EmailProducer emailProducer;
    private final RestaurantRegistrationTokenRepository restaurantRegistrationTokenRepository;
    private final RestaurantMenuRepository restaurantMenuRepository;

    public RestaurantService(RestaurantModelRepository restaurantRepository, EmailProducer emailProducer, RestaurantRegistrationTokenRepository restaurantRegistrationTokenRepository, RestaurantMenuRepository restaurantMenuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.emailProducer = emailProducer;
        this.restaurantRegistrationTokenRepository = restaurantRegistrationTokenRepository;
        this.restaurantMenuRepository = restaurantMenuRepository;
    }

    public String addrestaurant(RestaurantRegistration restaurantRegistration) {

        if (restaurantRepository.findByRestaurantEmail(restaurantRegistration.getRestaurantEmail()) != null) {
            return "restaurant with this email already exists!";
        }
        UUID confirmationToken = UUID.randomUUID();
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setRestaurantName(restaurantRegistration.getRestaurantName());
        restaurantModel.setRestaurantAddress(restaurantRegistration.getRestaurantAddress());
        restaurantModel.setRestaurantPhoneNumber(restaurantRegistration.getRestaurantPhoneNumber());
        restaurantModel.setRestaurantEmail(restaurantRegistration.getRestaurantEmail());
        restaurantModel.setRestaurantCuisineType(restaurantRegistration.getRestaurantCuisineType());
        restaurantModel.setRestaurantCoverImage(restaurantRegistration.getRestaurantCoverImage());
        restaurantModel.setConfirmationToken(confirmationToken);

        restaurantRepository.save(restaurantModel);
        restaurantRegistrationTokenRepository.save(new RestaurantRegistrationToken(confirmationToken, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), restaurantModel));
        Email email = new Email("restaurant", restaurantRegistration.getRestaurantEmail(), confirmationToken);
        emailProducer.sendEmailConfirmation(email);
        return "Application received! We will review your application and get back to you soon.";

    }

    public String updateRestaurantDetails(UUID token, RestaurantRegistration restaurantRegistration) {
        RestaurantModel restaurant = restaurantRepository.findByConfirmationToken(token);

        if (restaurantRegistration.getRestaurantAddress() != null) {
            restaurant.setRestaurantAddress(restaurantRegistration.getRestaurantAddress());
        }
        if (restaurantRegistration.getRestaurantCoverImage() != null) {
            restaurant.setRestaurantCoverImage(restaurantRegistration.getRestaurantCoverImage());
        }
        if (restaurantRegistration.getRestaurantCuisineType() != null) {
            restaurant.setRestaurantCuisineType(restaurantRegistration.getRestaurantCuisineType());
        }
        if (restaurantRegistration.getRestaurantEmail() != null) {
            restaurant.setRestaurantEmail(restaurantRegistration.getRestaurantEmail());
        }
        if (restaurantRegistration.getRestaurantName() != null) {
            restaurant.setRestaurantName(restaurantRegistration.getRestaurantName());
        }
        if (restaurantRegistration.getRestaurantPhoneNumber() != null) {
            restaurant.setRestaurantPhoneNumber(restaurantRegistration.getRestaurantPhoneNumber());
        }
        restaurantRepository.save(restaurant);
        return "Restaurant Details Updated";
    }

    public String addMenu(UUID token, List<MenuRegistration> restaurantMenu) {
        RestaurantModel restaurant = restaurantRepository.findByConfirmationToken(token);

        for (MenuRegistration menu : restaurantMenu) {
            restaurantMenuRepository.save(new RestaurantMenu(menu.getItemName(), menu.getItemImageUrl(), menu.getItemDescription(), menu.getItemPrice(), menu.getItemCategory(), restaurant, true));
        }

        return "MENU ADDED SUCCESSFULLY!";

    }

    public void update(UUID token, String itemName, MenuRegistration menu) {

        RestaurantModel restaurant = restaurantRepository.findByConfirmationToken(token);
        RestaurantMenu existingmenu = restaurantMenuRepository.findByRestaurantAndItemName(restaurant, itemName).orElseThrow();
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
        restaurantMenuRepository.save(existingmenu);

    }

    public RestaurantModel getrestaurantByToken(UUID token) {
        return restaurantRepository.findByConfirmationToken(token);
    }

    public String deleteItem(UUID token, String item_name) {

        RestaurantModel restaurant = restaurantRepository.findByConfirmationToken(token);
        RestaurantMenu existingmenu = restaurantMenuRepository.findByRestaurantAndItemName(restaurant, item_name).orElseThrow();
        restaurantMenuRepository.delete(existingmenu);
        return "Item deleted";
    }

    public Page<RestaurantMenu> getMenu() {
        return restaurantMenuRepository.findByPrice(PageRequest.of(0, 5));
    }

    public List<RestaurantModel> getrestaurants() {
        return restaurantRepository.findAll();
    }

}
