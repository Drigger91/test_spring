package com.bfhl.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class TestSpringController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World from spring boot";
    }

    @GetMapping("/first")
    public String first() {
        return "first";
    }

}
