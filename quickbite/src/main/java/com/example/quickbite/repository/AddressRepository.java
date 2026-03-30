package com.example.quickbite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quickbite.model.Address;
import com.example.quickbite.model.AppUser;

@Repository
@Transactional(readOnly = true)
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(AppUser user);
}
