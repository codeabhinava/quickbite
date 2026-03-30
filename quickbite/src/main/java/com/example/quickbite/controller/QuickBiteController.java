package com.example.quickbite.controller;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quickbite.model.Address;
import com.example.quickbite.service.AddressService;
import com.example.quickbite.service.CartService;
import com.example.quickbite.service.OrderService;
import com.example.quickbite.service.RestaurantService;

import lombok.extern.slf4j.Slf4j;

@Controller
@CrossOrigin
@Slf4j
@RequestMapping("/quickbite")
public class QuickBiteController {

    private final RestaurantService restaurantService;
    private final CartService cartService;
    private final AddressService addressService;
    private final OrderService orderService;

    public QuickBiteController(RestaurantService restaurantService, CartService cartService, AddressService addressService, OrderService orderService) {
        this.restaurantService = restaurantService;
        this.cartService = cartService;
        this.addressService = addressService;
        this.orderService = orderService;
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("featuredDishes", restaurantService.getFeaturedDishes());

        return "homepage";
    }

    @GetMapping("/viewRestaurants")
    public String view(Model model) {
        model.addAttribute("restaurants", restaurantService.getrestaurants());
        return "browseRestaurants";
    }

    @GetMapping("/{restaurantName}/viewMenu")
    public String restaurantmenu(@PathVariable String restaurantName, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        model.addAttribute("restaurant", restaurantService.getRestaurantByName(restaurantName));
        model.addAttribute("menuItems", restaurantService.getRestaurantMenu(restaurantName));
        model.addAttribute("cartQuantity", cartService.getQuantity(user, restaurantService.getRestaurantByName(restaurantName)));
        return "restaurantPage";
    }

    @PostMapping("/addeditem")
    public String addToCart(@RequestParam Long item_id, @RequestParam String restaurantName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        cartService.addtoCart(item_id, user);
        return "redirect:/quickbite/" + restaurantName + "/viewMenu";
    }

    @PostMapping("/increment")
    public String increaseQuantity(@RequestParam Long item_id, @RequestParam String restaurantName) {
        return addToCart(item_id, restaurantName);
    }

    @PostMapping("/decrement")
    public String decreaseQuantity(@RequestParam Long item_id, @RequestParam String restaurantName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        cartService.removefromCart(item_id, user);
        return "redirect:/quickbite/" + restaurantName + "/viewMenu";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long item_id, @RequestParam String restaurantName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        cartService.deletefromCart(item_id, user);
        return "redirect:/quickbite/" + restaurantName + "/viewMenu";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        model.addAttribute("cartItems", cartService.viewCart(user));
        model.addAttribute("cartTotal", cartService.cartTotal(user));
        return "cartPage";
    }

    @PostMapping("/cart/increment/{item_id}")
    public String increaseCart(@PathVariable long item_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        cartService.addtoCart(item_id, userName);
        return "redirect:/quickbite/cart";
    }

    @PostMapping("/cart/decrement/{item_id}")
    public String decreaseCart(@PathVariable long item_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        cartService.removefromCart(item_id, userName);
        return "redirect:/quickbite/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        model.addAttribute("cartItems", cartService.viewCart(userName));
        model.addAttribute("total", cartService.cartTotal(userName));
        model.addAttribute("addresses", addressService.getAddress(userName));
        return "checkoutPage";
    }

    @PostMapping("/checkout/order-placed")
    public String placeOrder(@ModelAttribute Address address, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        UUID orderId = UUID.randomUUID();
        model.addAttribute("order_id", orderId);
        addressService.saveAddress(user, address);
        orderService.addOrder(user, cartService.viewCart(user), orderId);
        cartService.orderPlaced(user);
        return "orderplacedPage";
    }

}
