package com.example.quickbite.model;

import lombok.Data;

@Data
public class MenuRegistration {

    private String itemName;
    private String itemImageUrl;
    private String itemDescription;
    private double itemPrice;
    private String itemCategory;
}
