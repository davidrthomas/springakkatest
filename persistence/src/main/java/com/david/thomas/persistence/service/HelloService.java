package com.david.thomas.persistence.service;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ShardRegion;
import akka.util.Timeout;
import com.david.thomas.persistence.*;
import org.springframework.stereotype.Service;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;

@Service
public class HelloService {

    private final ActorSystem system;
    //private final ActorRef gettingServiceSupervisor;
    FiniteDuration duration = FiniteDuration.create(1, TimeUnit.DAYS);
    Timeout timeout = Timeout.durationToTimeout(duration);

    public HelloService(ActorSystem system) {
        this.system = system;
    }

    public String sayHello(String name) throws Exception {


//        ActorRef greeter = system.actorOf(SPRING_EXTENSION_PROVIDER.get(system)
//                .props("greetingPerRequestActor"));
//
//
        ActorSelection greeter = system.actorSelection("akka://akka-spring-demo/user/$a/greetingServiceSupervisor");
        Future<Object> result = ask(greeter, new GreetingServiceSupervisorActor.Greet(name), timeout);
        return (String) Await.result(result, duration);
    }



    public String postEntity(String id, MyEntityActor.MyEntity entity) throws Exception {
//        ActorRef entityActor = system.actorOf(SPRING_EXTENSION_PROVIDER.get(system)
//                .props("myEntityActor"), id);
//
//        Future<Object> result = ask(entityActor, entity, timeout);
//        return (String) Await.result(result, duration);


        //Future<Object> result = ask(actorService.getRootActor(), new RootActor.MyEntityWithId(id, entity), timeout);
        //return (String) Await.result(result, duration);
        //ClusterSharding.get(system).start("MyEntity", Props.create(MyEntityActor.class), new Extractor());
        System.out.println("Got command");
        ActorRef counterRegion = ClusterSharding.get(system).shardRegion(PersistenceAppConfiguration.ShardingName);
        Future<Object> result = ask(counterRegion, new MyEntityWithId(id, entity), timeout);
        return (String) Await.result(result, duration);
    }
}
