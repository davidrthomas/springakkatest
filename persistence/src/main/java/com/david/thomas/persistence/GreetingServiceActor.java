package com.david.thomas.persistence;

import akka.actor.AbstractActor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GreetingServiceActor extends AbstractActor {
    private final GreetingService greetingService;

    public GreetingServiceActor(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, name -> {
                    getContext().getParent().tell(new GreetingServiceSupervisorActor.GreetBack(greetingService.greet(name)), getSelf());
                })
                .build();
    }
}