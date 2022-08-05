package com.bfhl.test.repositories;

import com.bfhl.test.entities.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public interface UserRepository extends MongoRepository<User, Integer> {

    List findByUsername(String name);

    Optional<User> findById(String id);

    boolean existsById(String id);
}
