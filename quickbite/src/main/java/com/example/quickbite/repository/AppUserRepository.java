package com.example.quickbite.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quickbite.model.AppUser;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByEmailId(String emailId);

    AppUser findByUserName(String userName);

    AppUser findByPhoneNumber(String phoneNumber);

    @Query("SELECT a FROM AppUser a WHERE a.confirmationToken = ?1")
    AppUser findByToken(UUID token);

    @Modifying
    @Transactional
    @Query("UPDATE AppUser a SET a.isenabled = true WHERE a.emailId = ?1")
    int enableAppUser(String emailId);

}
