package com.example.quickbite.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quickbite.service.CartService;
import com.example.quickbite.service.RestaurantService;

import lombok.extern.slf4j.Slf4j;

@Controller
@CrossOrigin
@Slf4j
@RequestMapping("/quickbite")
public class QuickBiteController {

    private final RestaurantService restaurantService;
    private final CartService cartService;

    public QuickBiteController(RestaurantService restaurantService, CartService cartService) {
        this.restaurantService = restaurantService;
        this.cartService = cartService;
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

    @PostMapping("/addeditem")
    public String addToCart(@RequestParam Long item_id, @RequestParam String restaurantName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        cartService.addtoCart(item_id, user);
        log.info(user);
        log.info(restaurantName);
        return "redirect:/quickbite/" + restaurantName + "/viewMenu";
    }

}
