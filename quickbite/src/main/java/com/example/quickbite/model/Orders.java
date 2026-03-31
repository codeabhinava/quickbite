package com.example.quickbite.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Orders {

    @Id
    private UUID order_id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantModel restaurant;

    @OneToMany(mappedBy = "order")
    private List<OrderItems> orderItems;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = true)
    private Bill bill;

    public Orders(UUID order_id, RestaurantModel restaurant, List<OrderItems> orderItems, AppUser user, Bill bill) {
        this.order_id = order_id;
        this.restaurant = restaurant;
        this.orderItems = orderItems;
        this.user = user;
        this.bill = bill;

    }
}
