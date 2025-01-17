package com.example.rms.services;

import com.example.rms.models.User;
import com.example.rms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Register a new user
    public User register(User user) {
        return userRepository.save(user);
    }

    // Login: Verify user credentials
    public Optional<User> login(String username, String password) {
        System.out.println("Debug: Login attempt for username: " + username);

        // Fetch user by username
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            System.out.println("Debug: User not found");
            throw new RuntimeException("User not found");
        }

        // Verify password
        User user = userOptional.get();
        System.out.println("Debug: Password provided: " + password);
        System.out.println("Debug: Password in database: " + user.getPassword());
        if (!user.getPassword().equals(password)) {
            System.out.println("Debug: Invalid password");
            throw new RuntimeException("Invalid password");
        }

        System.out.println("Debug: Login successful for username: " + username);
        return userOptional;
    }


    // Update password for a user
    public Optional<User> updatePassword(String username, String newPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        userOptional.ifPresent(user -> {
            user.setPassword(newPassword);
            userRepository.save(user);
        });
        return userOptional;
    }

    // Update role for a user
    public Optional<User> updateRole(String username, String newRole) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        userOptional.ifPresent(user -> {
            user.setRole(newRole);
            userRepository.save(user);
        });
        return userOptional;
    }
}
