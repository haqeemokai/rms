package com.example.rms.services;

import com.example.rms.models.MenuItem;
import com.example.rms.repositories.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    private MenuItemRepository menuItemRepository;

    // Add a new menu item
    public MenuItem addMenuItem(MenuItem menuItem) {
        if (menuItemRepository.existsById(menuItem.getId())) {
            throw new IllegalArgumentException("Menu Item ID already exists.");
        }
        return menuItemRepository.save(menuItem);
    }

    // Update an existing menu item
    public Optional<MenuItem> updateMenuItem(String id, MenuItem updatedMenuItem) {
        Optional<MenuItem> menuItemOptional = menuItemRepository.findById(id);
        menuItemOptional.ifPresent(menuItem -> {
            menuItem.setName(updatedMenuItem.getName());
            menuItem.setDescription(updatedMenuItem.getDescription());
            menuItem.setPrice(updatedMenuItem.getPrice());
            menuItem.setAvailable(updatedMenuItem.isAvailable());
            menuItemRepository.save(menuItem);
        });
        return menuItemOptional;
    }

    // Delete a menu item by ID
    public boolean deleteMenuItem(String id) {
        if (menuItemRepository.existsById(id)) {
            menuItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<MenuItem> getMenuItemById(String id) {
        return menuItemRepository.findById(id); // Assuming you have a `MenuRepository` that extends MongoRepository
    }


    // Retrieve all menu items
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }
}
