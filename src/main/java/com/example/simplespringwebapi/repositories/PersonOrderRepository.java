package com.example.simplespringwebapi.repositories;

import com.example.simplespringwebapi.entities.PersonOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonOrderRepository extends JpaRepository<PersonOrder, Long> {
    List<PersonOrder> findByPersonId(long id);
}
