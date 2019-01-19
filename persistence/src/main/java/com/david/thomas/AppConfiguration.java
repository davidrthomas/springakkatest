package com.david.thomas;

import akka.actor.ActorSystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(AppConfiguration.class, args);
    }

    @Bean
    public ActorSystem actorSystem(ApplicationContext applicationContext) {
        ActorSystem system = ActorSystem.create("akka-spring-demo");
        SpringExtension.SPRING_EXTENSION_PROVIDER.get(system)
                .initialize(applicationContext);
        return system;
    }
}