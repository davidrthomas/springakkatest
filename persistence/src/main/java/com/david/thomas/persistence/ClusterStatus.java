package com.david.thomas.persistence;


import akka.actor.AbstractActor;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.ClusterDomainEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.Member;

public class ClusterStatus extends AbstractActor {

    @Override
    public void preStart() throws Exception {
        Cluster.get(getContext().system()).subscribe(getSelf(), ClusterDomainEvent.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MemberUp.class, m -> {
                    System.out.println(m.member());
                })
                .match(MemberRemoved.class, m -> {
                    System.out.println(m.member());
                })
                .build();
    }

}