package com.example.quickbite.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quickbite.model.RestaurantRegistrationToken;

import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRegistrationTokenRepository extends JpaRepository<RestaurantRegistrationToken, Long> {

    Optional<RestaurantRegistrationToken> findByToken(UUID token);

    @Modifying
    @Transactional
    @Query("Update RestaurantRegistrationToken r SET r.confirmedAt= ?2 where r.token = ?1")
    int updateConfirmedAt(UUID token, LocalDateTime confirmedAt);
}
