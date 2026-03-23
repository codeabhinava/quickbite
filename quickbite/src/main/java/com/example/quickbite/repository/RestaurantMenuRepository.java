package com.example.quickbite.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quickbite.model.RestaurantMenu;
import com.example.quickbite.model.RestaurantModel;

@Repository
@Transactional(readOnly = true)
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Long> {

    @Query("Select r from RestaurantMenu r where r.restaurant = ?1")
    Page<RestaurantMenu> findByRestaurant(RestaurantModel Restaurant, Pageable pageable);

    Optional<RestaurantMenu> findByRestaurantAndItemName(RestaurantModel Restaurant, String itemName);

    @Query("Select r from RestaurantMenu r ORDER BY r.itemPrice ASC")
    Page<RestaurantMenu> findByPrice(Pageable pageable);

}
