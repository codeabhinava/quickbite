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

    private long total;
    private long platformFee;
    private long deliveryFee;
    private long discount;
    private long tax;

    public Bill(Orders orders, long total, long platformFee, long deliveryFee, long discount, long tax) {
        this.orders = orders;
        this.total = total;
        this.platformFee = platformFee;
        this.deliveryFee = deliveryFee;
        this.discount = discount;
        this.tax = tax;
    }
}
