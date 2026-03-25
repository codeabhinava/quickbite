package com.example.quickbite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quickbite.model.AppUser;
import com.example.quickbite.model.Cart;
import com.example.quickbite.model.RestaurantModel;

@Repository
@Transactional(readOnly = true)
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("Select c from Cart c where c.appUser = ?1 and c.menu.id = ?2  order by id")
    Optional<Cart> findByUserandItemId(AppUser user, Long id);

    @Query("Select c from Cart c where c.appUser = ?1 and c.restaurant = ?2  order by id")
    List<Cart> findByUserandRestaurant(AppUser user, RestaurantModel restaurant);

    @Query("SELECT SUM(c.quantity * c.menu.itemPrice) FROM Cart c WHERE c.appUser = ?1")
    Double getTotalPrice(AppUser user);

    @Query("Select c from Cart c where c.appUser = ?1 order by id")
    List<Cart> findByAppUser(AppUser appUser);

}
