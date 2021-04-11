package com.example.model;

import java.util.Objects;

public class Person {

    public static final Person DEFAULT_PERSON = new Person(0,"DEFAULT",999);
    public static final  Person PERSON_NO_FOUND = new Person(-1, "NO_FOUND", -999);

    private Integer id;
    private String name;
    private Integer age;

    public Person(Integer id, String name, Integer age) {
        this.id = Objects.requireNonNull(id, "The name is required");
        this.name = Objects.requireNonNull(name, "The name is required");
        this.age = Objects.requireNonNull(age, "The age is required");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
