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
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long billId;

    @ManyToOne
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    private double total;
    private double platformFee;
    private double deliveryFee;
    private double discount;
    private double tax;

    public Bill(Orders orders, double total, double platformFee, double deliveryFee, double discount, double tax) {
        this.orders = orders;
        this.total = total;
        this.platformFee = platformFee;
        this.deliveryFee = deliveryFee;
        this.discount = discount;
        this.tax = tax;
    }
}
