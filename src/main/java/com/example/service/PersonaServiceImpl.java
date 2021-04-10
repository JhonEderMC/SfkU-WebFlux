package com.example.service;

import com.example.model.Person;
import com.example.repository.PersonRepositoryInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonaServiceImpl implements PersonServiceInterface{

    @Autowired
    PersonRepositoryInter repo;

    @Override
    public Flux<Person> findAll() {
        return repo.findAll();
    }

    @Override
    public Mono<Person> findById(Integer personId) {
        return repo.findById(personId);
    }

    @Override
    public Mono<Person> save(Person person) {
        return null;
    }

    @Override
    public void delete(Integer personId) {

    }
}
