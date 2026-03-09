package com.example.quickbite.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quickbite.model.AppUser;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByEmailId(String emailId);

    @Query("SELECT a FROM AppUser a WHERE a.confirmationToken = ?1")
    AppUser findByToken(UUID token);

}
