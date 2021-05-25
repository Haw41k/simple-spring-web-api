package com.example.simplespringwebapi.repositories;

import com.example.simplespringwebapi.entities.PersonOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonOrderRepository extends JpaRepository<PersonOrder, Long> {
}
