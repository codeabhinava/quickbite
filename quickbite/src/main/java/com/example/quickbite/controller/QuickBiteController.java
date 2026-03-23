package com.example.quickbite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        model.addAttribute("featuredDishes", restaurantService.getFeaturedDishes());
        return "homepage";
    }

    @GetMapping("/viewRestaurants")
    public String view(Model model) {
        model.addAttribute("restaurants", restaurantService.getrestaurants());
        return "browseRestaurants";
    }

    @GetMapping("/{restaurantName}/viewMenu")
    public String restaurantmenu(@PathVariable String restaurantName, Model model) {
        model.addAttribute("restaurant", restaurantService.getRestaurantByName(restaurantName));
        model.addAttribute("menuItems", restaurantService.getRestaurantMenu(restaurantName));
        return "restaurantPage";
    }

}
