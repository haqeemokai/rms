package com.example.rms.controllers;

import com.example.rms.models.Order;
import com.example.rms.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // Create a new order
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.createOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    // Update order status
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateOrderStatus(@PathVariable String id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("Order not found"));
    }

    // View order history for a customer
    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable String customerId) {
        List<Order> orders = orderService.getOrderHistory(customerId);
        return ResponseEntity.ok(orders);
    }
}
