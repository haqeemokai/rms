package com.example.rms.repositories;

import com.example.rms.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCustomerId(String customerId); // Retrieve orders by customer ID
}
