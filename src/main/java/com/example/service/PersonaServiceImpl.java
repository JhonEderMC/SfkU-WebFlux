package com.example.service;

import com.example.model.Person;
import com.example.repository.PersonRepositoryInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

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
    public Flux<Person> findByName(String name){
        return repo.findByName(name);
    }

    @Override
    public Mono<Person> save(Person person) {
        return repo.save(person);
    }

    @Override
    public Flux<Person> saveAll(List<Person> personList){
        var peopleBefore = repo.findAll();

        var p = peopleBefore.toIterable(); //este iterable no es usable no se deja recorrer de ninguna forma

        return   peopleBefore.concatWith(Flux.fromIterable(personList))
                .flatMap(person -> repo.save(person));
    }

    @Override
    public void delete(Integer personId) {
        repo.delete(personId);
    }

    public Flux<Person> greatAndLessPersonAge(){
        var monOptGreat = repo.findAll()
                .collect(Collectors.maxBy(Comparator.comparing(Person::getAge)))
                .map(x->x.get());

        var monOptLess = repo.findAll()
                .collect(Collectors.minBy(Comparator.comparing(Person::getAge)))
                .map(x->x.get());

        return Flux.zip(monOptGreat, monOptLess)
                .flatMap(x-> Flux.just(x.getT2(),(x.getT2())));

    }

    public Mono<IntSummaryStatistics> statistics() {
        return repo.findAll()
                .collect(Collectors.summarizingInt(Person::getAge));
    }
}
