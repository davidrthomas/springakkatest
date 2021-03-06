package com.david.thomas.persistence.controller;

import com.david.thomas.persistence.MyEntityActor;
import com.david.thomas.persistence.MyEntityActor2;
import com.david.thomas.persistence.service.HelloService;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/api/name/{name}")
    public String index(@PathVariable("name") String name) throws Exception {
        return helloService.sayHello(name);
    }

    @PostMapping("/api/entity/{id}")
    public String index(@RequestBody MyEntityActor2.MyEntity entity, @PathVariable("id") String id) throws Exception {
        return helloService.postEntity(id, entity);
    }
}