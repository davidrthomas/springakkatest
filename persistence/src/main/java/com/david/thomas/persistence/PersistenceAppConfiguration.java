package com.david.thomas.persistence;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ShardRegion;

import com.david.thomas.persistence.actor.SharedStorageUsage;

import com.typesafe.config.ConfigFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

import static com.david.thomas.persistence.SpringExtension.SPRING_EXTENSION_PROVIDER;


@SpringBootApplication
public class PersistenceAppConfiguration {

    private static String env;
    public static final String ActorSystemName = "ShardingSystem";
    public static final String ShardingName = "ShardingActor";


    public static void main(String[] args) {
        env = args[0];
        SpringApplication.run(PersistenceAppConfiguration.class, args);
    }

    @Bean
    public ActorSystem actorSystem(ApplicationContext applicationContext) throws IOException {


        //ClassPathResource resource = new ClassPathResource("/application.properties.bak");
        //Properties props = PropertiesLoaderUtils.loadProperties(resource);

        //Config config = ConfigFactory.parseProperties(props);

        ActorSystem system = ActorSystem.create(ActorSystemName,  ConfigFactory.load(env));
        //system.actorOf(Props.create(SharedLeveldbStore.class), "store");
        if(env == "node.xml") {
            system.actorOf(Props.create(SharedStorageUsage.class), "sharedStorage");
            system.actorOf(Props.create(ClusterStatus.class));
        }

        ClusterSharding.get(system).start(ShardingName, Props.create(MyEntityActor2.class), new Extractor());

        SPRING_EXTENSION_PROVIDER.get(system)
                .initialize(applicationContext);
        return system;
    }

    private class Extractor implements ShardRegion.MessageExtractor {
        @Override
        public String entityId(Object message) {
//			System.out.println("1> " + message);
            if (message instanceof MyEntityWithId) {
                return String.valueOf(((MyEntityWithId) message).getId());
            } else {
                return null;
            }
        }

        @Override
        public Object entityMessage(Object message) {
            if (message instanceof MyEntityWithId) {
                System.out.println("extracting my entity command");
                return (MyEntityWithId) message;
            } else {
                return message;
            }
        }

        @Override
        public String shardId(Object message) {
            return "1";
            //int numberOfShards = 20;
/*
            if (message instanceof MyEntityWithId) {
                return String.valueOf(((MyEntityWithId) message).getIdInt() % numberOfShards);
            } else {
                return null;
            }*/
        }
    }
}