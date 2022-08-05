package com.bfhl.test.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/name")
    public String first() {
        return "Name";
    }

    @GetMapping("/name/id")
    public String second(@RequestParam(name = "id", required = false, defaultValue = "id not provided") String id) {
        return id;
    }

}
