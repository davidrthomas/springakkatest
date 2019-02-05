package com.david.thomas.persistence;

import akka.persistence.AbstractPersistentActor;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MyEntityActor extends AbstractPersistentActor {
    private MyEntity myEntity = new MyEntity("");

    @Override
    public Receive createReceiveRecover() {
        return receiveBuilder()
                .match(MyEntity.class, myEntity -> {
                    this.myEntity = myEntity;
                    System.out.println(String.format("Recovering %s", myEntity.getName()));
                })
                .build();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MyEntity.class, myEntity -> {
                    persist(myEntity, (x) -> {
                        System.out.println("received my entity command");
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