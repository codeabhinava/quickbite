package com.example.quickbite.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ResturantRegistration {

    private String resturantName;
    private String resturantAddress;
    private String resturantPhoneNumber;
    private String resturantEmail;
    private String resturantCuisineType;

}
