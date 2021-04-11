package com.example.controller;

import com.example.model.Person;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void getPeopleValidateResponse() {
      Flux<Person> personFlux = webTestClient.get().uri("/person/all").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .returnResult(Person.class)
                .getResponseBody();

      //Si corres solo este test se esperan 11 personas, si los corres todos serian 10, porque el test de eliminar quita una
        //por eso es importante el orden, con esto se soluciona esto
        StepVerifier.create(personFlux.log("Receiving values !!!"))
                .expectNextCount(11)
                .verifyComplete();
    }

    @Test
    @Order(2)
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
    @Order(4)
    void getPersonByIdNoFound(){
        webTestClient.get().uri("person/id".concat("/{id}"), "-10")
                .exchange()
                .expectBody()
                .jsonPath("-1", "NO_FOUND", "-999");
    }

    @Test
    @Order(5)
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
    @Order(6)
    void getPeopleByNameNoFound(){
        webTestClient.get().uri("/person/name".concat("/{name}"), "#4-0LSA&")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("[]");
    }

    @Test
    @Order(7)
    void savePerson(){
        webTestClient.post().uri("/person").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .body(Mono.just(Person.DEFAULT_PERSON), Person.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Person.class)
                //.jsonPath("$.id").isEqualTo("0")
                //.jsonPath("$.name").isEqualTo("DEFAULT")
                //.jsonPath("$.age").isEqualTo("999");
                .consumeWith(p->{
                    var person = p.getResponseBody();
                    assertEquals(0, person.getId());
                    assertEquals("DEFAULT", person.getName());
                    assertEquals(999, person.getAge());
                });
    }

    @Test
    @Order(9)
    void savePeople(){
        webTestClient.post().uri("/person/all").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .body(Flux.just(Person.DEFAULT_PERSON, Person.PERSON_NO_FOUND), Person.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(Person.class)
                .consumeWith(people->{
                         var list = people.getResponseBody();
                         list.forEach(p->{
                             assertNotNull(p.getId());
                             assertNotNull(p.getAge());
                             assertNotNull(p.getName());
                            });
                        }
                );
    }

    @Test
    @Order(8)
    void deletedById(){
        webTestClient.delete().uri("/person".concat("/{id}"), "0")
                .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }
}