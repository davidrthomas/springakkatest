package com.david.thomas.persistence.service;

import com.david.thomas.persistence.MyEntityWithId;
import com.david.thomas.persistence.Thing;
import com.david.thomas.persistence.repository.ThingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class ThingService {

    @Autowired
    private ThingRepository thingRepository;

    public void doThing(MyEntityWithId entity){
        Thing thing = new Thing(entity.getIdInt(), LocalDateTime.now().toString());
        thingRepository.save(thing);
    }
}
