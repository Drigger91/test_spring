package com.bfhl.test.entities;

import lombok.*;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;


@Data
@ToString
@Entity
@Document("Test1")
public class User {
    @Id
    @Generated
    private String id;

    private String username;

    private String password;

    private String email;

}
