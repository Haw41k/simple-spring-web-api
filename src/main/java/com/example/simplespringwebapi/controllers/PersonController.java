package com.example.simplespringwebapi.controllers;

import com.example.simplespringwebapi.entities.Person;
import com.example.simplespringwebapi.exceptions.ElementNotFoundException;
import com.example.simplespringwebapi.repositories.PersonRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    final private PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable long id){
        return personRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id, "person"));
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @PutMapping("/{id}")
    public Person setPerson(@PathVariable long id, @RequestBody Person newPerson) {
        return personRepository.findById(id).map(person -> {

            person.setName(newPerson.getName());
            person.setInn(newPerson.getInn());
            person.setTelephone(newPerson.getTelephone());
            person.setRegionCode(newPerson.getRegionCode());
            person.setRegionName(newPerson.getRegionName());
            person.setCityName(newPerson.getCityName());
            person.setStreetName(newPerson.getStreetName());
            person.setHouseNumber(newPerson.getHouseNumber());
            person.setHouseCorpsNumber(newPerson.getHouseCorpsNumber());
            person.setHouseRoomNumber(newPerson.getHouseRoomNumber());

            return personRepository.save(person);

        }).orElseThrow(() -> new ElementNotFoundException(id, "person"));
    }

    @DeleteMapping("/{id}")
    void deletePerson(@PathVariable long id) {
        try {
            personRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ElementNotFoundException(id, "person");
        }
    }
}
