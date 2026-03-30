package com.example.quickbite.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.quickbite.model.AppUser;
import com.example.quickbite.model.Cart;
import com.example.quickbite.model.RestaurantMenu;
import com.example.quickbite.model.RestaurantModel;
import com.example.quickbite.repository.AppUserRepository;
import com.example.quickbite.repository.CartRepository;
import com.example.quickbite.repository.RestaurantMenuRepository;

@Service
public class CartService {

    private final RestaurantMenuRepository restaurantMenuRepositroy;
    private final CartRepository cartRepository;
    private final AppUserRepository userRepository;

    public CartService(RestaurantMenuRepository restaurantMenuRepositroy, CartRepository cartRepository, AppUserRepository userRepository) {
        this.restaurantMenuRepositroy = restaurantMenuRepositroy;
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

    public void removefromCart(Long item_id, String userName) {

        RestaurantMenu menu = restaurantMenuRepositroy.findById(item_id).orElseThrow();

        AppUser user = userRepository.findByUserName(userName);

        Cart cart = cartRepository.findByUserandItemId(user, menu.getItemID()).orElseThrow();
        cart.setQuantity(cart.getQuantity() - 1);
        if (cart.getQuantity() == 0) {
            cartRepository.delete(cart);
        } else {

            cartRepository.save(cart);
        }
    }

    public void deletefromCart(Long item_id, String userName) {

        RestaurantMenu menu = restaurantMenuRepositroy.findById(item_id).orElseThrow();

        AppUser user = userRepository.findByUserName(userName);

        Cart cart = cartRepository.findByUserandItemId(user, menu.getItemID()).orElseThrow();

        cartRepository.delete(cart);
    }

    public Map<Long, Integer> getQuantity(String userName, RestaurantModel restaurant) {
        AppUser user = userRepository.findByUserName(userName);
        List<Cart> cart = cartRepository.findByUserandRestaurant(user, restaurant);
        return cart.stream().collect(Collectors.toMap(c -> c.getMenu().getItemID(), Cart::getQuantity));
    }

    public List<Cart> viewCart(String userName) {
        AppUser user = userRepository.findByUserName(userName);
        List<Cart> cart = cartRepository.findByAppUser(user);
        if (cart.isEmpty()) {
            return cart;
        } else {

            Cart item = cart.get(cart.size() - 1);
            RestaurantModel latestRestaurant = item.getRestaurant();
            return cartRepository.findByUserandRestaurant(user, latestRestaurant);
        }
    }

    public Double cartTotal(String userName) {
        AppUser user = userRepository.findByUserName(userName);
        return cartRepository.getTotalPrice(user);
    }

    public void orderPlaced(String userName) {
        AppUser user = userRepository.findByUserName(userName);
        List<Cart> cart = cartRepository.findByAppUser(user);
        Cart item = cart.get(cart.size() - 1);
        List<Cart> cartByRestaurant = cartRepository.findByUserandRestaurant(user, item.getRestaurant());
        cartRepository.deleteAll(cartByRestaurant);
    }

}
