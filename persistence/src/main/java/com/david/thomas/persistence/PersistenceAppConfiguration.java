package com.david.thomas.persistence;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class PersistenceAppConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(PersistenceAppConfiguration.class, args);
    }

    @Bean
    public ActorSystem actorSystem(ApplicationContext applicationContext) throws IOException {


        //ClassPathResource resource = new ClassPathResource("/application.properties");
        //Properties props = PropertiesLoaderUtils.loadProperties(resource);

        //Config config = ConfigFactory.parseProperties(props);

        ActorSystem system = ActorSystem.create("ClusterSystem");
        SpringExtension.SPRING_EXTENSION_PROVIDER.get(system)
                .initialize(applicationContext);
        return system;
    }
}