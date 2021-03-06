package com.example.repository;

import com.example.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class PersonRepositoryImplement implements PersonRepositoryInter{

    private static Logger logger = LoggerFactory.getLogger(PersonRepositoryImplement.class);

    private List<Person> personList;

    public PersonRepositoryImplement() {
        this.personList = start();
    }



    @Override
    public Flux<Person> findAll() {
        return Flux.fromIterable(personList);
    }

    @Override
    public Mono<Person> findById(Integer personId) {
          return Flux.fromIterable(personList)
                    .filter(person -> person.getId().equals(personId))
                    .switchIfEmpty(Mono.just(Person.PERSON_NO_FOUND))
                    .flatMap(Mono::just)
                    .log()
                    .next();
    }

    @Override
    public Flux<Person> findByName(String name){
        return Flux.fromIterable(personList)
                .filter(person ->
                     person.getName().toLowerCase(Locale.ROOT)
                            .indexOf(name.trim().toLowerCase(Locale.ROOT)) >-1
                ).switchIfEmpty(Mono.empty())
                .map(person -> person)
                .log();
    }

    @Override
    public Mono<Person> save(Person person) {
         return Mono.just(person)
                .flatMap(p ->{
                    if (personList.contains(p)){
                        return Mono.empty();
                    }else {
                        personList.add(p);
                        return Mono.just(p);
                    }
                });
    }

    //Remover de la base de datos simulados
    @Override
    public void delete(Integer personId) {
        boolean delete = false;
        Person person = null;
        for (Person p:personList){
            if (p.getId().equals(personId)){
                delete=true;
                person = p;
                break;
            }
        }
        if (delete){
            removeIf(person);
        }
    }

    private void removeIf( Person person){
        personList.remove(person);
    }

    private List<Person> start() {
        return new ArrayList(defaultPersons());
    }

    private List<Person> defaultPersons(){
        return List.of(
                new Person(1 , "Arbey", 30),
                new Person(2 , "Daniel", 15),
                new Person(3 , "Luisa", 17),
                new Person(5 , "Oscar", 37),
                new Person(6 , "Andrea", 16),
                new Person(7 , "Camila", 27),
                new Person(8 , "Jhonatan", 23),
                new Person(9 , "Jhon", 29),
                new Person(10 , "Jhovan", 24),
                new Person(11 , "Leidy", 18),
                new Person(12 , "Sandra", 24)
        );
    }
}
