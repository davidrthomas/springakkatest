package com.david.thomas.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.persistence.journal.leveldb.SharedLeveldbStore;
import com.david.thomas.actor.SharedStorageUsage;
import org.springframework.stereotype.Service;

import static com.david.thomas.SpringExtension.SPRING_EXTENSION_PROVIDER;

@Service
public class ActorService {


    private final ActorRef rootActor;
    private final ActorSystem actorSystem;
    //private final ActorRef store;
    private final ActorRef sharedStorage;

    public ActorService(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
        this.rootActor = actorSystem.actorOf(SPRING_EXTENSION_PROVIDER.get(actorSystem)
                .props("rootActor"));
        //this.store = actorSystem.actorOf(Props.create(SharedLeveldbStore.class), "store");
        this.sharedStorage = actorSystem.actorOf(Props.create(SharedStorageUsage.class), "storageUsage");
        //this.store = actorSystem.actorOf(SPRING_EXTENSION_PROVIDER.get(actorSystem).props("sharedLeveldbStore"));

    }

    public ActorRef getRootActor() {
        return rootActor;
    }
}
