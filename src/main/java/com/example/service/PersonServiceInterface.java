package com.example.service;

import com.example.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.IntSummaryStatistics;
import java.util.List;

public interface PersonServiceInterface {
    Flux<Person> findAll();
    Mono<Person> findById(Integer personId);
    Flux<Person> findByName(String name);
    Mono<Person> save(Person person);
    Flux<Person> saveAll(List<Person> personList);
    void delete(Integer personId);
    Flux<Person> greatAndLessPersonAge();
    Mono<IntSummaryStatistics> statistics();
}
