package com.example.quickbite.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quickbite.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, UUID> {

}
