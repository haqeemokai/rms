package com.example.rms.services;

import com.example.rms.models.Customer;
import com.example.rms.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    // Register a new customer
    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Update customer information
    public Optional<Customer> updateCustomer(String id, Customer updatedCustomer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        customerOptional.ifPresent(customer -> {
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhone(updatedCustomer.getPhone());
            customer.setAddress(updatedCustomer.getAddress());
            customerRepository.save(customer);
        });
        return customerOptional;
    }

    // View customer history
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Find a customer by ID
    public Optional<Customer> findCustomerById(String id) {
        return customerRepository.findById(id);
    }
}
