package com.example.quickbite.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Data
public class ResturantMenu {

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
    @JoinColumn(name = "resturant_id", nullable = false)
    private ResturantModel resturant;

    public ResturantMenu(String itemName, String itemImageUrl, String itemDescription, double itemPrice, String itemCategory, ResturantModel resturant, boolean isAvailable) {
        this.itemName = itemName;
        this.itemImageUrl = itemImageUrl;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.resturant = resturant;
        this.isAvailable = isAvailable;
    }
}
