package com.example.quickbite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quickbite.model.ResturantModel;

@Repository
@Transactional(readOnly = true)
public interface ResturantModelRepository extends JpaRepository<ResturantModel, Long> {

}
