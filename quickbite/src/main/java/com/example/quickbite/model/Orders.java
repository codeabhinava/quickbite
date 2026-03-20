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
    @JoinColumn(name = "resturant_id", nullable = false)
    private ResturantModel resturant;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private ResturantMenu menu;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    public Orders(UUID order_id, ResturantModel resturant, ResturantMenu menu, AppUser user) {
        this.order_id = order_id;
        this.resturant = resturant;
        this.menu = menu;
        this.user = user;
    }
}
