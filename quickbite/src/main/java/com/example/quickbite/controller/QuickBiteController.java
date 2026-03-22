package com.example.quickbite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.quickbite.service.RestaurantService;

@Controller
@CrossOrigin
@RequestMapping("/quickbite")
public class QuickBiteController {

    private final RestaurantService restaurantService;

    public QuickBiteController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("menu", restaurantService.getMenu());
        return "homepage";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("restaurants", restaurantService.getrestaurants());
        return "restaurantPage";
    }

}
