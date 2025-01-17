package com.example.rms.controllers;

import com.example.rms.models.User;
import com.example.rms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    // Login a user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User authenticatedUser = userService.login(user.getUsername(), user.getPassword()).get();
            return ResponseEntity.ok(authenticatedUser);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(401).body(ex.getMessage());
        }
    }

    // Update a user's password
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestParam String username, @RequestParam String newPassword) {
        Optional<User> userOptional = userService.updatePassword(username, newPassword);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    // Update a user's role
    @PutMapping("/role")
    public ResponseEntity<?> updateRole(@RequestParam String username, @RequestParam String newRole) {
        Optional<User> userOptional = userService.updateRole(username, newRole);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok("Role updated successfully");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
