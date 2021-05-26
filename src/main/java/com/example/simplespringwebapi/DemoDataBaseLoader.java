package com.example.simplespringwebapi;

import com.example.simplespringwebapi.entities.Person;
import com.example.simplespringwebapi.entities.PersonOrder;
import com.example.simplespringwebapi.repositories.PersonOrderRepository;
import com.example.simplespringwebapi.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Configuration
public class DemoDataBaseLoader {
    private static final Logger log = LoggerFactory.getLogger(SimpleSpringWebApiApplication.class);

    @Bean
    public CommandLineRunner demo(PersonRepository personRepository, PersonOrderRepository orderRepository) {
        return (args) -> {

            for (int i = 0; i < 5; i++) {
                Person person = new Person();

                person.setPersonName("User_" + i);
                person.setInn(910200000000L + i);
                person.setTelephone(79789090000L + i);
                person.setRegionCode(91);
                person.setRegionName("Крым");
                person.setCityName("Симферополь");
                person.setStreetName("Евпаторийское ш.");
                person.setHouseNumber(1 + i * 2);
                person.setHouseCorpsNumber(1);
                person.setHouseRoomNumber(10 + i * 3);

                log.info("added new:\n" + personRepository.save(person));
            }

            for (int i = 0; i < 4; i++) {
                PersonOrder order = new PersonOrder();

                order.setPersonId(1L);
                order.setDescription("Заказ номер " + i + ". [товары]");
                order.setSum(BigDecimal.valueOf(54.2F * (i + 2)).setScale(2, RoundingMode.DOWN));

                log.info("added new:\n" + orderRepository.save(order));
            }
        };
    }
}
