package com.example.quickbite.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class RestaurantRegistration {

    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhoneNumber;
    private String restaurantEmail;
    private String restaurantCuisineType;
    private String restaurantCoverImage;

}
