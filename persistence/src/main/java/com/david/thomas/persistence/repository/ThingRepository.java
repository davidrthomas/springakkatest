package com.david.thomas.persistence.repository;


import com.david.thomas.persistence.Thing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThingRepository extends JpaRepository<Thing, Integer> {
}
