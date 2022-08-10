package com.bfhl.test.controller;


import com.bfhl.test.entities.User;
import com.bfhl.test.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;

    @GetMapping
    public String function() {
        return "Hi from  '/user' controller";
    }

    @GetMapping("/get")
    public List<?> getUsers() throws JsonProcessingException {

        System.out.println("Hey");
        return this.userService.getAll();
    }
    @PostMapping("/add")
    public String addUser(@RequestBody User user) throws JsonProcessingException {
        return this.userService.addUser(user);

    }
    @CachePut(key = "email",value = "User")
    @PatchMapping("/update/{email}")
    public ResponseEntity<?> updateUser(@PathVariable String email, @RequestBody User user) throws JsonProcessingException {

        return this.userService.updateUser(email, user) == null ? ResponseEntity.status(400).body("User was not found with this mail") : ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{email}")
    public String deleteUser(@PathVariable String email) {
        return this.userService.deleteUser(email);
    }
}
