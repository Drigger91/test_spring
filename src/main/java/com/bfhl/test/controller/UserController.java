package com.bfhl.test.controller;


import com.bfhl.test.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private com.bfhl.test.services.userService userService;

    @GetMapping
    public String function() {
        return "Hi from  '/user' controller";
    }

    @GetMapping("/get")
    public List<User> getUsers() {
        return this.userService.getAll();
    }

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        return this.userService.addUser(user);

    }

    @PatchMapping("/update/{email}")
    public ResponseEntity<?> updateUser(@PathVariable String email, @RequestBody User user) {

        return this.userService.updateUser(email, user) == null ? ResponseEntity.status(400).body("User was not found with this mail") : ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{email}")
    public String deleteUser(@PathVariable String email) {
        return this.userService.deleteUser(email);
    }
}
