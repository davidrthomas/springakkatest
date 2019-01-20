package com.david.thomas.persistence;

import org.springframework.stereotype.Component;

@Component
public class GreetingService {

    public String greet(String name) throws InterruptedException {
        if(name.equals("slow"))
            Thread.sleep(10000);
        return "Hello, " + name;
    }
}