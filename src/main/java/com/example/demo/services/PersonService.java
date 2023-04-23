package com.example.demo.services;

import com.example.demo.models.Person;

public interface PersonService {
    Person findPersonAge(String name) throws IllegalAccessException;

    void addPeopleFromFile();

    Person getOldestName();

    Person getStatistic(String name);
}
