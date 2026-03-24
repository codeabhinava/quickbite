package com.example.quickbite.service;

import org.springframework.stereotype.Service;

import com.example.quickbite.model.AppUser;
import com.example.quickbite.model.Cart;
import com.example.quickbite.model.RestaurantMenu;
import com.example.quickbite.model.RestaurantModel;
import com.example.quickbite.repository.AppUserRepository;
import com.example.quickbite.repository.CartRepository;
import com.example.quickbite.repository.RestaurantMenuRepository;
import com.example.quickbite.repository.RestaurantModelRepository;

@Service
public class CartService {

    private final RestaurantMenuRepository restaurantMenuRepositroy;
    private final RestaurantModelRepository restaurantRepository;
    private final CartRepository cartRepository;
    private final AppUserRepository userRepository;

    public CartService(RestaurantMenuRepository restaurantMenuRepositroy, RestaurantModelRepository restaurantRepository, CartRepository cartRepository, AppUserRepository userRepository) {
        this.restaurantMenuRepositroy = restaurantMenuRepositroy;
        this.restaurantRepository = restaurantRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public void addtoCart(Long item_id, String userName) {
        RestaurantMenu menu = restaurantMenuRepositroy.findById(item_id).orElseThrow();
        RestaurantModel restaurant = menu.getRestaurant();

        AppUser user = userRepository.findByUserName(userName);
        if (cartRepository.findByUserandItemId(user, menu.getItemID()).isEmpty()) {
            Cart cart = new Cart();
            cart.setAppUser(user);
            cart.setMenu(menu);
            cart.setRestaurant(restaurant);
            cart.setQuantity(1);
            cartRepository.save(cart);
        } else {
            Cart cart = cartRepository.findByUserandItemId(user, menu.getItemID()).orElseThrow();
            cart.setQuantity(cart.getQuantity() + 1);
            cartRepository.save(cart);
        }

    }

}
