package com.example.quickbite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quickbite.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
