package com.example.simplespringwebapi;

import com.example.simplespringwebapi.entities.Person;
import com.example.simplespringwebapi.entities.PersonOrder;
import com.example.simplespringwebapi.exceptions.ElementNotFoundException;
import com.example.simplespringwebapi.repositories.PersonOrderRepository;
import com.example.simplespringwebapi.repositories.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonOrderControllerTests {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonOrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void deleteAll() {
        orderRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    public void getOrders() throws Exception {
        Person person = createNewTestPerson();
        person = personRepository.save(person);

        PersonOrder order = createNewTestOrder(person.getId());
        orderRepository.save(order);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(orderRepository.findAll())));
    }

    @Test
    public void getOrder() throws Exception {
        Person person = createNewTestPerson();
        person = personRepository.save(person);

        PersonOrder order = createNewTestOrder(person.getId());
        orderRepository.save(order);

        mockMvc.perform(get("/api/orders/{id}", order.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(order)));
    }

    @Test
    public void getNoExistentOrder() throws Exception {
        mockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        mvcResult.getResolvedException().getClass().equals(ElementNotFoundException.class));
    }

    @Test
    public void addNewOrder() throws Exception {
        Person person = createNewTestPerson();
        person = personRepository.save(person);

        PersonOrder order = createNewTestOrder(person.getId());

        mockMvc.perform(
                post("/api/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.personId").value(order.getPersonId()))
                .andExpect(jsonPath("$.sum").value(order.getSum().doubleValue()))
                .andExpect(jsonPath("$.description").value(order.getDescription()));
    }

    @Test
    public void addNewOrderWithNotExistentPersonId() throws Exception {
        PersonOrder order = createNewTestOrder(Long.MAX_VALUE);

        this.mockMvc.perform(
                post("/api/orders")
                        .content(this.objectMapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        mvcResult.getResolvedException().getClass().equals(ElementNotFoundException.class));
    }

    @Test
    public void setOrder() throws Exception {
        Person person = createNewTestPerson();
        person = personRepository.save(person);

        PersonOrder order = createNewTestOrder(person.getId());
        order = orderRepository.save(order);

        order.setDescription("updated order");

        mockMvc.perform(
                put("/api/orders/{id}", order.getId())
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(order)));
    }

    @Test
    public void deleteOrder() throws Exception {
        Person person = createNewTestPerson();
        person = personRepository.save(person);

        PersonOrder order = createNewTestOrder(person.getId());
        order = orderRepository.save(order);


        mockMvc.perform(delete("/api/orders/{id}", order.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/orders/{id}", order.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePersonWithChildOrders() throws Exception {
        Person person = createNewTestPerson();
        person = personRepository.save(person);

        List<PersonOrder> orders = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            orders.add(createNewTestOrder(person.getId()));
        }

        orders = orderRepository.saveAll(orders);

        mockMvc.perform(delete("/api/persons/{id}", person.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/persons/{id}", person.getId()))
                .andExpect(status().isNotFound());

        for (PersonOrder order : orders) {
            mockMvc.perform(get("/api/orders/{id}", order.getId()))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    public void getPersonOrders() throws Exception {
        Person person = createNewTestPerson();
        person = personRepository.save(person);

        List<PersonOrder> orders = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            orders.add(createNewTestOrder(person.getId()));
        }

        orders = orderRepository.saveAll(orders);

        mockMvc.perform(get("/api/orders")
                .param("person-id", person.getId().toString())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(orders)));
    }


    private PersonOrder createNewTestOrder(Long personId) {
        PersonOrder order = new PersonOrder();

        order.setPersonId(personId);
        order.setSum(BigDecimal.valueOf(5433.2F).setScale(2, RoundingMode.DOWN));
        order.setDescription("Тестовый заказ");

        return order;
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
