package com.example.quickbite.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Orders {

    @Id
    private UUID order_id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantModel restaurant;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private RestaurantMenu menu;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    private long quantity;

    public Orders(UUID order_id, RestaurantModel restaurant, RestaurantMenu menu, AppUser user, long quantity) {
        this.order_id = order_id;
        this.restaurant = restaurant;
        this.menu = menu;
        this.user = user;
        this.quantity = quantity;

    }
}
