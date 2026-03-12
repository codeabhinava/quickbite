package com.example.quickbite.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quickbite.model.ResturantRegistration;
import com.example.quickbite.service.ResturantRegistrationService;
import com.example.quickbite.service.ResturantService;

@RestController
@RequestMapping("/quickbite/resturants")
public class ResturantController {

    private final ResturantService resturantService;
    private final ResturantRegistrationService resturantRegistrationService;

    public ResturantController(ResturantService resturantService, ResturantRegistrationService resturantRegistrationService) {
        this.resturantService = resturantService;
        this.resturantRegistrationService = resturantRegistrationService;
    }

    @PostMapping("/new")
    public String newResturant(@RequestBody ResturantRegistration resturantRegistration) {

        resturantService.addResturant(resturantRegistration);
        return "Resturant added successfully!";

    }

    @GetMapping("/confirm")
    public String confirmUser(@RequestParam UUID token) {
        resturantRegistrationService.confirmToken(token);
        return "Resturant confirmed successfully!";
    }

}
