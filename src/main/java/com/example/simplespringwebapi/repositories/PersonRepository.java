package com.example.simplespringwebapi.repositories;

import com.example.simplespringwebapi.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByTelephone(Long tel);
    Optional<Person> findByInn(Long inn);
}
