package com.bfhl.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TestSpringApplication {

    public static void main(String[] args) {
        System.out.println("Hello world from spring boot");
        SpringApplication.run(TestSpringApplication.class, args);
    }

}
