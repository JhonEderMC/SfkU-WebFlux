package com.example.service;

import com.example.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonServiceInterface {
    Flux<Person> findAll();
    Mono<Person> findById(Integer personId);
    Mono<Person> save(Person person);
    void delete(Integer personId);
}
