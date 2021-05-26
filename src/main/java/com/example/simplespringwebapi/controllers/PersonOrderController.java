package com.example.simplespringwebapi.controllers;

import com.example.simplespringwebapi.entities.PersonOrder;
import com.example.simplespringwebapi.exceptions.ElementNotFoundException;
import com.example.simplespringwebapi.repositories.PersonOrderRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class PersonOrderController {
    PersonOrderRepository orderRepository;

    public PersonOrderController(PersonOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<PersonOrder> getAll() {
        return orderRepository.findAll();
    }

    @GetMapping(params = {"person-id"})
    List<PersonOrder> getPersonOrders(@RequestParam("person-id") long id) {
        return orderRepository.findByPersonId(id);
    }

    @GetMapping("/{id}")
    public PersonOrder getOrder(@PathVariable long id){
        return orderRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id, "order"));
    }

    @PostMapping
    PersonOrder addOrder(@RequestBody PersonOrder order){
        try {
            return orderRepository.save(order);
        } catch (DataIntegrityViolationException ex) {
            throw  new ElementNotFoundException(order.getPersonId(), "person");
        }
    }

    @PutMapping("/{id}")
    PersonOrder setOrder(@PathVariable long id, @RequestBody PersonOrder newOrder) {
        return orderRepository.findById(id).map(order -> {

            order.setSum(newOrder.getSum());
            order.setDescription(newOrder.getDescription());

            return orderRepository.save(order);

        }).orElseThrow(() -> new ElementNotFoundException(id, "order"));
    }

    @DeleteMapping("/{id}")
    void deleteOrder(@PathVariable long id) {
        try {
            orderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ElementNotFoundException(id, "order");
        }
    }
}
