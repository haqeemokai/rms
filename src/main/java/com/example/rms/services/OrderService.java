package com.example.rms.services;

import com.example.rms.models.Order;
import com.example.rms.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    // Create a new order
    public Order createOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING"); // Default status
        return orderRepository.save(order);
    }

    // Update order status
    public Optional<Order> updateOrderStatus(String id, String status) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        orderOptional.ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
        return orderOptional;
    }

    // Get order history for a customer
    public List<Order> getOrderHistory(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}
