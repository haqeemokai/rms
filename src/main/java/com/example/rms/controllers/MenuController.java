package com.example.rms.controllers;

import com.example.rms.models.MenuItem;
import com.example.rms.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    // Add a new menu item
    @PostMapping("/add")
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        MenuItem savedMenuItem = menuService.addMenuItem(menuItem);
        return ResponseEntity.ok(savedMenuItem);
    }

    // Update an existing menu item
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMenuItem(@PathVariable String id, @RequestBody MenuItem updatedMenuItem) {
        return menuService.updateMenuItem(id, updatedMenuItem)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(new MenuItem())); // Return an empty MenuItem or null equivalent
    }

    // Delete a menu item
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable String id) {
        if (menuService.deleteMenuItem(id)) {
            return ResponseEntity.ok("Menu item deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Menu item not found");
        }
    }

    // Retrieve all menu items
    @GetMapping("/all")
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menuService.getAllMenuItems());
    }
}
