package com.example.quickbite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private ResturantMenu menu;

    @ManyToOne
    @JoinColumn(name = "resturant_id", nullable = false)
    private ResturantModel resturant;

    private int quantity;

    public Cart(ResturantMenu menu, ResturantModel resturant, int quantity) {
        this.menu = menu;
        this.resturant = resturant;
        this.quantity = quantity;

    }

}
