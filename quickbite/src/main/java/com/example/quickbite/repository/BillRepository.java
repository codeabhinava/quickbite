package com.example.quickbite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quickbite.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

}
