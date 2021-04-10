package com.example.controller;

import com.example.model.Person;
import com.example.service.PersonServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonServiceInterface service;

    @GetMapping("/all")
    public Flux<Person> getPeople(){
     return  service.findAll();
    }
}
