package com.example.controller;

import com.example.model.Person;
import com.example.service.PersonServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonServiceInterface service;

    @GetMapping("/all")
    public Flux<Person> getPeople(){
     return  service.findAll();
    }

    @GetMapping("/id/{id}")
    public Mono<Person> getPersonById(@PathVariable Integer id){
        return service.findById(id);
    }

    @GetMapping("/name/{name}")
    public Flux<Person> getPeopleByName(@PathVariable String name){
        return service.findByName(name);
    }

}
