package com.example.repository;

import com.example.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface PersonRepositoryInter {

    Flux<Person> findAll();
    Mono<Person> findById(Integer personId);
    Flux<Person> findByName(String name);
    Mono<Person> save(Person person);
    void delete(Integer personId);

}
