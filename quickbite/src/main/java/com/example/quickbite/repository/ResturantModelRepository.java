package com.example.quickbite.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quickbite.model.ResturantModel;

@Repository
@Transactional(readOnly = true)
public interface ResturantModelRepository extends JpaRepository<ResturantModel, Long> {

    ResturantModel findByResturantEmail(String resturantEmail);

    ResturantModel findByConfirmationToken(UUID confirmationToken);

    @Transactional
    @Modifying
    @Query("Update ResturantModel r SET r.isConfirmed = true WHERE r.confirmationToken = ?1")
    int updateIsConfirmedBy(UUID confirmationToken);

}
