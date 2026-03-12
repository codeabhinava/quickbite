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
public class ResturantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resturantId;

    private String resturantName;
    private String resturantAddress;
    private String resturantPhoneNumber;
    private String resturantEmail;
    private String resturantCuisineType;
    private UUID confirmationToken;
    private boolean isConfirmed;

    public ResturantModel(String resturantName, String resturantAddress, String resturantPhoneNumber, String resturantEmail, String resturantCuisineType, UUID confirmationToken) {
        this.resturantName = resturantName;
        this.resturantAddress = resturantAddress;
        this.resturantPhoneNumber = resturantPhoneNumber;
        this.resturantEmail = resturantEmail;
        this.resturantCuisineType = resturantCuisineType;
        this.confirmationToken = confirmationToken;
    }
}
