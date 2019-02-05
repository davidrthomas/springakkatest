package com.david.thomas.persistence;

import java.io.Serializable;

public class MyEntityWithId implements Serializable {
    private static final long serialVersionUID = -3993657009177564101L;
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
