package com.example.quickbite.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quickbite.model.UserRegistration;
import com.example.quickbite.producer.EmailProducer;
import com.example.quickbite.producer.OTPProducer;
import com.example.quickbite.service.ConfirmationService;
import com.example.quickbite.service.UserService;

@RestController
@RequestMapping("/quickbite/users")
public class UserController {

    private final OTPProducer otpProducer;
    private final EmailProducer emailProducer;
    private final UserService userService;
    private final ConfirmationService confirmationService;

    public UserController(OTPProducer otpProducer, EmailProducer emailProducer, UserService userService, ConfirmationService confirmationService) {
        this.otpProducer = otpProducer;
        this.emailProducer = emailProducer;
        this.userService = userService;
        this.confirmationService = confirmationService;
    }

    @PostMapping("/send-otp")
    public String sendOTP() {
        // Logic to generate OTP and send it using OTPProducer
        otpProducer.sendOTP("123456", "user123");
        return "OTP sent successfully!";
    }

    @PostMapping("/new-user")
    public String createUser(@RequestBody UserRegistration userRegistration) {
        userService.createUser(userRegistration);
        return "User created successfully!";
    }

    /*@GetMapping("/confirm")
    public String confirmUser(@RequestParam UUID token) {
        confirmationService.confirmToken(token);
        return "User confirmed successfully!";
    }*/
}
