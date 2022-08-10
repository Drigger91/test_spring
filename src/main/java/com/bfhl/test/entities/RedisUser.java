package com.bfhl.test.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("User")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RedisUser implements Serializable{
    @Id
    private String id;

    private String username;

    private String password;

    private String email;



}
