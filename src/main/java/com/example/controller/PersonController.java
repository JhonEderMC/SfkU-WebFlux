package com.example.controller;

import com.example.model.Person;
import com.example.service.PersonServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Person> savePerson(@RequestBody Person person){
        return service.save(person);
    }

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Person> savePeople(@RequestBody List<Person> personList){
        return service.saveAll(personList);
    }

}
