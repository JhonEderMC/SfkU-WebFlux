package com.example.repository;

import com.example.model.Person;
import org.springframework.expression.spel.ast.Literal;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonaRepositoryImplement implements PersonRepositoryInter{

    private List<Person> personList;

    public PersonaRepositoryImplement() {
        this.personList = start();
    }



    @Override
    public Flux<Person> findAll() {
        return null;
    }

    @Override
    public Mono<Person> findById(Integer personId) {
        return null;
    }

    @Override
    public Mono<Person> save(Person person) {
        return null;
    }

    @Override
    public void delete(Integer personId) {

    }

    private List<Person> start() {
        return new ArrayList(defaultPersons());
    }

    private List<Person> defaultPersons(){
        return List.of(
                new Person(1 , "Arbey", 31),
                new Person(2 , "Daniel", 15),
                new Person(3 , "Luisa", 19),
                new Person(5 , "Oscar", 37),
                new Person(6 , "Andrea", 20),
                new Person(7 , "Camila", 27)
        );
    }
}
