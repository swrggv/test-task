package com.example.demo.services;

import com.example.demo.models.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService{
    @Override
    public Person findPersonAge(String name) {
        return new Person(1, "Person", 4);
    }
}
