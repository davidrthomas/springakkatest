package com.david.thomas.persistence;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import static com.david.thomas.persistence.SpringExtension.SPRING_EXTENSION_PROVIDER;

//@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GreetingServiceSupervisorActor extends AbstractActor {

//    private final ActorRef greetingServiceActor
//            = getContext().actorOf(Props.create(GreetingServiceActor.class), "greetingService");

    private final ActorRef greetingServiceActor = getContext().actorOf(SPRING_EXTENSION_PROVIDER.get(getContext().getSystem())
            .props("greetingServiceActor"));

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Greet.class, g -> {
                String name = g.getName();
                //getSender().tell(greetingService.greet(name), getSelf());
                //getContext().getParent().tell(greetingService.greet(name), getSelf());
                greetingServiceActor.tell(g.getName(), getSelf());
                //greetingServiceActor.as
            })
            .match(GreetBack.class, g -> {
                getSender().tell(g.getMessage(), getSelf());
            })
                .match(Object.class, o -> {
                    System.out.println("error");
                })
            .build();
    }

    public static class Greet {
        private final String name;

        public Greet(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class GreetBack {
        private final String message;

        public GreetBack(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}