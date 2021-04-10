package com.example.controller;

import com.example.model.Person;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")

class PersonControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getPeopleValidateResponse() {
      Flux<Person> personFlux = webTestClient.get().uri("/person/all").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .returnResult(Person.class)
                .getResponseBody();

        StepVerifier.create(personFlux.log("Receiving values !!!"))
                .expectNextCount(11)
                .verifyComplete();
    }

    @Test
    void getPeopleValidate(){
        webTestClient.get().uri("/person/all").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBodyList(Person.class)
                .consumeWith( people ->{
                    List<Person> peopleList = people.getResponseBody();
                    peopleList.forEach(person -> {
                        assertTrue(person.getId() !=null);
                        assertTrue(person.getName() !=null);
                        assertTrue(person.getAge() !=null);
                    });
                });

    }
}