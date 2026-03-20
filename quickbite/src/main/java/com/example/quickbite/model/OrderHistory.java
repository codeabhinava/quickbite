package com.example.quickbite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "resturant_id", nullable = false)
    private ResturantModel resturant;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private ResturantMenu menu;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    private double bill;

    public OrderHistory(Orders orders, ResturantModel resturant, ResturantMenu menu, AppUser user, double bill) {
        this.orders = orders;
        this.resturant = resturant;
        this.menu = menu;
        this.user = user;
        this.bill = bill;
    }

}
