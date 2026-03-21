package com.example.quickbite.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class RestaurantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhoneNumber;
    private String restaurantEmail;
    private String restaurantCuisineType;
    private UUID confirmationToken;
    private boolean isConfirmed;

    public RestaurantModel(String restaurantName, String restaurantAddress, String restaurantPhoneNumber, String restaurantEmail, String restaurantCuisineType, UUID confirmationToken) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantPhoneNumber = restaurantPhoneNumber;
        this.restaurantEmail = restaurantEmail;
        this.restaurantCuisineType = restaurantCuisineType;
        this.confirmationToken = confirmationToken;
    }
}
