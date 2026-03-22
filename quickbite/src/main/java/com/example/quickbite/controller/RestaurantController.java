package com.example.quickbite.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quickbite.model.MenuRegistration;
import com.example.quickbite.model.RestaurantRegistration;
import com.example.quickbite.service.RestaurantRegistrationService;
import com.example.quickbite.service.RestaurantService;

@RestController
@RequestMapping("/quickbite/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantRegistrationService restaurantRegistrationService;

    public RestaurantController(RestaurantService restaurantService, RestaurantRegistrationService restaurantRegistrationService) {
        this.restaurantService = restaurantService;
        this.restaurantRegistrationService = restaurantRegistrationService;
    }

    @PostMapping("/new")
    public String newrestaurant(@RequestBody RestaurantRegistration restaurantRegistration) {

        restaurantService.addrestaurant(restaurantRegistration);
        return "restaurant added successfully!";

    }

    @GetMapping("/confirm")
    public String confirmUser(@RequestParam UUID token) {
        restaurantRegistrationService.confirmToken(token);
        return "restaurant confirmed successfully!";
    }

    @PatchMapping("/{token}/updateRestaurantDetails")
    public String updateDetails(@PathVariable UUID token, @RequestBody RestaurantRegistration restaurantRegistration) {
        return restaurantService.updateRestaurantDetails(token, restaurantRegistration);
    }

    @PostMapping("/{token}/addMenu")
    public String addMenu(@PathVariable UUID token, @RequestBody List<MenuRegistration> menu) {
        return restaurantService.addMenu(token, menu);

    }

    @PatchMapping("/{token}/{item_name}/updateMenu")
    public String updateMenu(@PathVariable UUID token, @PathVariable String item_name, @RequestBody MenuRegistration menu) {
        restaurantService.update(token, item_name, menu);
        return "MENU UPDATED SUCCESSFULLY";
    }

    @DeleteMapping("/{token}/{item_name}/deleteMenu")
    public String deleteMenu(@PathVariable UUID token, @PathVariable String item_name) {
        return restaurantService.deleteItem(token, item_name);
    }

}
