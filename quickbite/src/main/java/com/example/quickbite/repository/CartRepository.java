package com.example.quickbite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quickbite.model.AppUser;
import com.example.quickbite.model.Cart;

@Repository
@Transactional(readOnly = true)
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("Select c from Cart c where c.appUser = ?1 and c.menu.id = ?2")
    Optional<Cart> findByUserandItemId(AppUser user, Long id);

}
