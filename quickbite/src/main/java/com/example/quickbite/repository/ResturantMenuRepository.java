package com.example.quickbite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.quickbite.model.ResturantMenu;
import com.example.quickbite.model.ResturantModel;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ResturantMenuRepository extends JpaRepository<ResturantMenu, Long> {

    @Query("Select r from ResturantMenu r where r.resturant = ?1")
    List<ResturantMenu> findByResturant(ResturantModel resturant);

    Optional<ResturantMenu> findByResturantAndItemName(ResturantModel resturant, String itemName);

}
