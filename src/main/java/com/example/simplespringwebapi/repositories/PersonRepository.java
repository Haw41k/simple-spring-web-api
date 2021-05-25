package com.example.simplespringwebapi.repositories;

import com.example.simplespringwebapi.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
