package com.david.thomas;

import akka.actor.AbstractActor;
import akka.persistence.AbstractPersistentActor;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MyEntityActor extends AbstractPersistentActor {
    private MyEntity myEntity = new MyEntity("");

    @Override
    public Receive createReceiveRecover() {
        return receiveBuilder()
                .match(MyEntity.class, myEntity -> {
                    this.myEntity = myEntity;
                })
                .build();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MyEntity.class, myEntity -> {
                    persist(myEntity, (x) -> {
                        String prevValue = this.myEntity.getName();
                        this.myEntity = myEntity;
                        getSender().tell(prevValue, getSelf());
                    });
                })
                .build();
    }

    @Override
    public String persistenceId() {
        return getSelf().path().name();
    }

    public static class MyEntity implements Serializable {
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