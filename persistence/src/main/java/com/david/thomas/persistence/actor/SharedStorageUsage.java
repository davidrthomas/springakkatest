package com.david.thomas.persistence.actor;

import akka.actor.*;
import akka.persistence.journal.leveldb.SharedLeveldbJournal;
import akka.persistence.journal.leveldb.SharedLeveldbStore;

import java.util.Optional;


public class SharedStorageUsage extends AbstractActor {

    ActorRef store = getContext().actorOf(Props.create(SharedLeveldbStore.class), "store");

    @Override
    public void preStart() throws Exception {
        //String path = "akka.tcp://example@127.0.0.1:2552/user/store";
        //ActorSelection selection = getContext().actorSelection(path);
        //selection.tell(new Identify(1), getSelf());

        store.tell(new Identify(1), getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ActorIdentity.class, ai -> {
                    if (ai.correlationId().equals(1)) {
                        Optional<ActorRef> store = ai.getActorRef();
                        if (store.isPresent()) {
                            SharedLeveldbJournal.setStore(store.get(), getContext().getSystem());
                        } else {
                            throw new RuntimeException("Couldn't identify store");
                        }
                    }
                })
                .build();
    }
}