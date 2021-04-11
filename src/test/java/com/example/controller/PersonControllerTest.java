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
import reactor.core.publisher.Mono;
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
                        assertNotNull(person.getId() );
                        assertNotNull(person.getName());
                        assertNotNull(person.getAge());
                    });
                });

    }

    @Test
    void getPersonByIdValidate(){
        webTestClient.get().uri("/person/id".concat("/{id}"), "7").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("7", "Camila", "27");
    }

    @Test
    void getPersonByIdNoFound(){
        webTestClient.get().uri("person/id".concat("/{id}"), "-10")
                .exchange()
                .expectBody()
                .jsonPath("-1", "NO_FOUND", "-999");
    }

    @Test
    void getPeopleByNameValidate(){
        webTestClient.get().uri("/person/name".concat("/{name}"), "o")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBodyList(Person.class)
                .consumeWith(
                        people ->{
                            List<Person> pList = people.getResponseBody();
                            pList.forEach(person -> {
                                assertNotNull(person.getId() );
                                assertNotNull(person.getName());
                                assertNotNull(person.getAge());
                            });
                });
    }

    @Test
    void getPeopleByNameNoFound(){
        webTestClient.get().uri("/person/name".concat("/{name}"), "#4-0LSA&")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("[]");
    }

    @Test
    void savePerson(){
        webTestClient.post().uri("/person").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .body(Mono.just(Person.DEFAULT_PERSON), Person.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("DEFAULT")
                .jsonPath("$.age").isEqualTo("999");
    }
}