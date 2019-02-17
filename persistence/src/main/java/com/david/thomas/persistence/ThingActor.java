package com.david.thomas.persistence;

import akka.actor.AbstractActor;
import com.david.thomas.persistence.service.ThingService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ThingActor extends AbstractActor {


    private final ThingService thingService;

    public ThingActor(ThingService thingService) {
        this.thingService = thingService;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MyEntityWithId.class, myEntity -> {
            System.out.println(this.getClass().toString());
            thingService.doThing(myEntity);
            getSender().tell(LocalDate.now().toString(), getSelf());
        })
        .build();
    }
}
