package com.bfhl.test.services;

import com.bfhl.test.entities.User;
import com.bfhl.test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class userService {
    @Autowired
    private final UserRepository repository;

    public userService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public String addUser(User user) {
        if (user.getUsername() == null) {
            return "Username Required";
        }
        if (user.getEmail() == null) {
            return "Email Required";
        }
        if (user.getPassword() == null) {
            return "Password Required";
        }
        if (findByEmail(user.getEmail()) != null) {
            return "User already exist with this mail";
        }
        repository.save(user);
        System.out.println("Master B Branch");
        System.out.println("Master A Branch");
        return "User posted successfully!";
    }

    public String deleteUser(String email) {
        User user = this.findByEmail(email);;
        if(Objects.isNull(user)) return "No user found with this mail";
        repository.delete(user);
        return "User deleted!!";
    }

    public User findByEmail(String email) {
        List<User> list = repository.findAll();
        for (User user : list) {
            if (Objects.equals(user.getEmail(), email)) return user;
        }
        return null;
    }

    public User updateUser(String email, User obj) {
        String username = obj.getUsername();
        String password = obj.getPassword();
        User user = this.findByEmail(email);
        if (user == null) return null;
        repository.delete(user);
        if (username != null) {
            user.setUsername(username);
        }
        if (password != null) {
            user.setPassword(password);
        }
        if (username == null && password == null) return null;
        return repository.save(user);
    }
}
