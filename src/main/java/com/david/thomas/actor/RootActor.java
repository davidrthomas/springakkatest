package com.david.thomas.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.util.Timeout;
import com.david.thomas.GreetingServiceSupervisorActor;
import com.david.thomas.MyEntityActor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RootActor extends AbstractActor {

    FiniteDuration duration = FiniteDuration.create(1, TimeUnit.DAYS);
    Timeout timeout = Timeout.durationToTimeout(duration);

    private final ActorRef greetingServiceSupervisorActor
            = getContext().actorOf(Props.create(GreetingServiceSupervisorActor.class), "greetingServiceSupervisor");

    public RootActor() {
        System.out.println(greetingServiceSupervisorActor.path());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MyEntityWithId.class, x -> {
                    Future<Object> result;
                    Optional<ActorRef> actor = getContext().findChild(x.getId());
                    if(actor.isPresent()) {
                        result = ask(actor.get(), x.getMyEntity(), timeout);
                    }
                    else {
                        result = ask(getContext().actorOf(Props.create(MyEntityActor.class), x.getId()), x.getMyEntity(), timeout);
                    }
                    getSender().tell(Await.result(result, duration), getSelf());
                })
                .build();
    }

    public static class MyEntityWithId {
        private final MyEntityActor.MyEntity myEntity;
        private final String id;

        public MyEntityWithId(String id, MyEntityActor.MyEntity myEntity) {
            this.myEntity = myEntity;
            this.id = id;
        }

        public MyEntityActor.MyEntity getMyEntity() {
            return myEntity;
        }

        public String getId() {
            return id;
        }
    }
}