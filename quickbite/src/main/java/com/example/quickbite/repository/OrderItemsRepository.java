package com.example.quickbite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quickbite.model.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

}
