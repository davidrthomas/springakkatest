package com.david.thomas.seed.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {


    @PostMapping("/api")
    public String index()   {
        return "ok";
    }
}