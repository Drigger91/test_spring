package com.bfhl.test.repositories;

import com.bfhl.test.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface UserRepository{


    User findByEmail(String name);

    Optional<User> findById(String id);

    boolean existsById(String id);

    List<?> findAll() throws JsonProcessingException;

    User save(User user) throws JsonProcessingException;

    void delete(User user);
}
