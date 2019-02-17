package com.david.thomas.persistence;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import com.david.thomas.persistence.service.ThingService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;

import static com.david.thomas.persistence.SpringExtension.SPRING_EXTENSION_PROVIDER;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MyEntityActor2 extends AbstractActor {
    private MyEntity myEntity = new MyEntity("");
    private ActorRef thingActor;

    @Override
    public void preStart() throws Exception {
        thingActor = getContext().getSystem().actorOf(SPRING_EXTENSION_PROVIDER.get(getContext().getSystem())
                .props("thingActor"));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MyEntityWithId.class, myEntity -> {
                    System.out.println(this.getClass().toString());
                    thingActor.tell(myEntity, getSelf());
                    getSender().tell(LocalDate.now().toString(), getSelf());
                })
                .build();
    }

    public static class MyEntity implements Serializable {
        private static final long serialVersionUID = 7465100161517213802L;
        private String name;

        public MyEntity() {
        }

        public MyEntity(@JsonProperty String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}