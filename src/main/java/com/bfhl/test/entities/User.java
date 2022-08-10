package com.bfhl.test.entities;

import lombok.*;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import java.io.Serializable;


@Data
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Document("Test1")
public class User{
    @Id
    private String id;

    private String username;

    private String password;

    private String email;

    public User(String id, String name) {
        this.id = id;
        this.username = name;
    }
}
