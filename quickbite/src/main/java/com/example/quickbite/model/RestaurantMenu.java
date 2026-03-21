package com.example.quickbite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class RestaurantMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemID;

    private String itemName;
    private String itemImageUrl;
    private String itemDescription;
    private double itemPrice;
    private String itemCategory;
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantModel restaurant;

    public RestaurantMenu(String itemName, String itemImageUrl, String itemDescription, double itemPrice, String itemCategory, RestaurantModel restaurant, boolean isAvailable) {
        this.itemName = itemName;
        this.itemImageUrl = itemImageUrl;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.restaurant = restaurant;
        this.isAvailable = isAvailable;
    }
}
