package com.david.thomas.controller;

import com.david.thomas.MyEntityActor;
import com.david.thomas.service.HelloService;
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
    public String index(@RequestBody MyEntityActor.MyEntity entity, @PathVariable("id") String id) throws Exception {
        return helloService.postEntity(id, entity);
    }
}