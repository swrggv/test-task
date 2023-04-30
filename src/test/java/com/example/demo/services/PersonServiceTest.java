package com.example.demo.services;

import com.example.demo.models.Person;
import com.example.demo.reposotories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = "application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PersonServiceTest {
    @Autowired
    private PersonServiceImpl personService;
    @Autowired
    private PersonRepository personRepository;
    private Person one;
    private Person two;

    @BeforeEach
    void setUp() {
        one = new Person("один", 20);
        two = new Person("два", 50);
        personRepository.save(one);
        personRepository.save(two);
    }

    @Test
    void findPersonAge() {
        Person result = personService.findPersonAge(one.getName());
        System.out.println(result);
        assertThat(result, equalTo(one));
    }

    @Test
    void findPersonAgeWithIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> personService.findPersonAge("person"));
        assertEquals("Допустимы только символы кириллицы", ex.getMessage());
    }

    @Test
    void findPersonAgeWhichNameIsNotExist() {
        Person result = personService.findPersonAge("Оля");
        assertThat(result.getName(), equalTo("Оля"));
    }

    @Test
    void getOldestName() {
        Person result = personService.getOldestName();
        System.out.println(result);
        assertThat(two, equalToObject(two));
    }
}