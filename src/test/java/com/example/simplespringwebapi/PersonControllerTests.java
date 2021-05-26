package com.example.simplespringwebapi;

import com.example.simplespringwebapi.entities.Person;
import com.example.simplespringwebapi.exceptions.ElementNotFoundException;
import com.example.simplespringwebapi.repositories.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTests {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getPersons() throws Exception {
        Person person = createNewTestPerson();
        person = personRepository.save(person);

        mockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(personRepository.findAll())));

        personRepository.delete(person);
    }

    @Test
    public void getPerson() throws Exception {
        Person person = createNewTestPerson();
        person = personRepository.save(person);

        mockMvc.perform(get("/api/persons/{id}", person.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(person)));

        personRepository.delete(person);
    }

    @Test
    public void getNoExistentPerson() throws Exception {
        mockMvc.perform(get("/api/persons/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        mvcResult.getResolvedException().getClass().equals(ElementNotFoundException.class));
    }

    @Test
    public void addNewPerson() throws Exception {
        Person person = createNewTestPerson();

        person.setTelephone(9999999999L);

        mockMvc.perform(
                post("/api/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.personName").value(person.getPersonName()))
                .andExpect(jsonPath("$.inn").value(person.getInn()))
                .andExpect(jsonPath("$.telephone").value(person.getTelephone()))
                .andExpect(jsonPath("$.regionCode").value(person.getRegionCode()))
                .andExpect(jsonPath("$.regionName").value(person.getRegionName()))
                .andExpect(jsonPath("$.cityName").value(person.getCityName()))
                .andExpect(jsonPath("$.streetName").value(person.getStreetName()))
                .andExpect(jsonPath("$.houseNumber").value(person.getHouseNumber()))
                .andExpect(jsonPath("$.houseCorpsNumber").value(person.getHouseCorpsNumber()))
                .andExpect(jsonPath("$.houseRoomNumber").value(person.getHouseRoomNumber()));

        personRepository.delete(personRepository.findByTelephone(person.getTelephone()).get());
    }

    @Test
    public void addNewPersonWithNotUniqueInn() throws Exception {
        Person person = createNewTestPerson();
        Person person2 = personRepository.save(createNewTestPerson());

        person.setTelephone(999999L);

        mockMvc.perform(
                post("/api/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isConflict());

        personRepository.delete(person2);
    }

    @Test
    public void addNewPersonWithNotUniqueTelephone() throws Exception {
        Person person = createNewTestPerson();
        Person person2 = personRepository.save(createNewTestPerson());

        person.setInn(null);

        mockMvc.perform(
                post("/api/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isConflict());

        personRepository.delete(person2);
    }

    @Test
    public void addNewPersonWithNullTelephone() throws Exception {
        Person person = createNewTestPerson();
        Person person2 = personRepository.save(createNewTestPerson());

        person.setInn(null);
        person.setTelephone(null);

        mockMvc.perform(
                post("/api/persons")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isConflict());

        personRepository.delete(person2);
    }

    @Test
    public void setPerson() throws Exception {
        Person person = createNewTestPerson();

        person = personRepository.save(person);
        person.setPersonName("updated person");

        mockMvc.perform(
                put("/api/persons/{id}", person.getId())
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(person)));

        personRepository.delete(person);
    }

    @Test
    public void deletePerson() throws Exception {
        Person person = createNewTestPerson();
        person = personRepository.save(person);

        mockMvc.perform(delete("/api/persons/{id}", person.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/persons/{id}", person.getId()))
                .andExpect(status().isNotFound());
    }

    private Person createNewTestPerson() {
        Person person = new Person();

        person.setPersonName("test person");
        person.setInn(910299999999L);
        person.setTelephone(79789099999L);
        person.setRegionCode(91);
        person.setRegionName("Крым");
        person.setCityName("Симферополь");
        person.setStreetName("Евпаторийское ш.");
        person.setHouseNumber(122);
        person.setHouseCorpsNumber(1);
        person.setHouseRoomNumber(10);

        return person;
    }
}
