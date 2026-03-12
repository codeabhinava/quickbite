package com.example.quickbite.model;

import java.time.LocalDateTime;
import java.util.UUID;

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
public class ResturantRegistrationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID token;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "resturant_id")
    private ResturantModel resturantModel;

    public ResturantRegistrationToken(UUID token, LocalDateTime createdAt, LocalDateTime expiresAt, ResturantModel resturantModel) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.resturantModel = resturantModel;
    }
}
