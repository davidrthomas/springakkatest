package com.david.thomas.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Thing {
    @Id
    private Integer id;
    private String thing;

    public Thing() {}

    public Thing(Integer id, String thing) {
        this.id = id;
        this.thing = thing;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }
}
