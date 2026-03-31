package com.example.quickbite.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.quickbite.model.AppUser;
import com.example.quickbite.model.Bill;
import com.example.quickbite.model.Cart;
import com.example.quickbite.model.OrderItems;
import com.example.quickbite.model.Orders;
import com.example.quickbite.repository.AppUserRepository;
import com.example.quickbite.repository.BillRepository;
import com.example.quickbite.repository.OrderItemsRepository;
import com.example.quickbite.repository.OrdersRepository;

@Service
public class OrderService {

    private OrderItemsRepository orderItemsRepository;
    private OrdersRepository ordersRepository;
    private AppUserRepository userRepository;
    private BillRepository billRepository;

    public OrderService(OrderItemsRepository orderItemsRepository, OrdersRepository ordersRepository, AppUserRepository userRepository, BillRepository billRepository) {
        this.orderItemsRepository = orderItemsRepository;
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.billRepository = billRepository;
    }

    public void addOrder(String userName, List<Cart> cart, UUID orderId) {
        AppUser user = userRepository.findByUserName(userName);
        Orders order = new Orders();
        order.setUser(user);
        order.setOrder_id(orderId);

        order.setRestaurant(cart.get(0).getRestaurant());
        ordersRepository.save(order);
        List<OrderItems> orderitems = new ArrayList<>();
        long total = 0;
        for (Cart c : cart) {
            OrderItems items = new OrderItems();
            items.setMenu(c.getMenu());
            items.setOrder(order);
            items.setQuantity(c.getQuantity());
            items.setItemTotal(c.getMenu().getItemPrice() * c.getQuantity());
            orderItemsRepository.save(items);
            orderitems.add(items);
            total += c.getMenu().getItemPrice() * c.getQuantity();
        }
        order.setOrderItems(orderitems);
        Bill bill = new Bill(order, total, 4, (0.013 * total), 0, (0.025 * total));
        billRepository.save(bill);
        order.setBill(bill);
        ordersRepository.save(order);
    }
}
