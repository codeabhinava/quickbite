package com.example.quickbite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Orders order;

    @ManyToOne
    private RestaurantMenu menu;

    private int quantity;
    private double itemTotal;

    public OrderItems(Orders order, RestaurantMenu menu, int quantity, double itemTotal) {
        this.order = order;
        this.menu = menu;
        this.quantity = quantity;
        this.itemTotal = itemTotal;
    }
}
