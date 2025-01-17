package com.example.rms.controllers;

import com.example.rms.models.Customer;
import com.example.rms.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    // Register a new customer
    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    // Update customer information
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable String id, @RequestBody Customer updatedCustomer) {
        return customerService.updateCustomer(id, updatedCustomer)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("Customer not found"));
    }

    // View customer history
    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // View a specific customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable String id) {
        return customerService.findCustomerById(id)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("Customer not found"));
    }

}
