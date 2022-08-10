package com.bfhl.test.service;

import com.bfhl.test.entities.User;

import com.bfhl.test.repositories.MongoRepo;
import com.bfhl.test.repositories.RedisUserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private MongoRepo mongoUserRepo;
    private RedisUserRepo redisUserRepo;

    public List<?> getAll() throws JsonProcessingException {
        var result = redisUserRepo.findAll();
        if(result!=null){
            log.info("redis");
            return (List<?>) result;
        }
        log.info("Mongo");
        return mongoUserRepo.findAll();
    }
    public String function(){
        return "Hi from User Service";
    }

    public String addUser(User user) throws JsonProcessingException {
        if (user.getUsername() == null) {
            return "Username Required";
        }
        if (user.getEmail() == null) {
            return "Email Required";
        }
        if (user.getPassword() == null) {
            return "Password Required";
        }
        if (mongoUserRepo.findByEmail(user.getEmail())!=null) {
            return "User Already exist" ;
        }
        User mongo_user = mongoUserRepo.save(user);
        User redis_user = redisUserRepo.save(mongo_user);
        return redis_user.getEmail();
    }

    public String deleteUser(String email) {
        User user = redisUserRepo.findByEmail(email);
        if(Objects.isNull(user)) return "No user found with this mail";
        redisUserRepo.delete(user);
        mongoUserRepo.delete(user);
        return "User Successfully deleted!";
    }



    public User updateUser(String email, User obj) throws JsonProcessingException {
        String username = obj.getUsername();
        String password = obj.getPassword();
        User user = redisUserRepo.findByEmail(email);
        if (user == null) return null;
        redisUserRepo.delete(user);
        mongoUserRepo.delete(user);
        if (username != null) {
            user.setUsername(username);
        }
        if (password != null) {
            user.setPassword(password);
        }
        if (username == null && password == null) return null;
        mongoUserRepo.save(user);
        return redisUserRepo.save(user);
    }
}
